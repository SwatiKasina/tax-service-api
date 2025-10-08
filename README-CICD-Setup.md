# CI/CD Pipeline Setup for Tax Service API

This guide walks you through setting up the complete CI/CD pipeline using GitHub Actions and AWS CodePipeline to deploy your Java Spring Boot application to ECS.

## Prerequisites

1. **AWS Account**: With permissions to create resources
2. **GitHub Repository**: https://github.com/SwatiKasina/tax-service-api
3. **AWS CLI**: Installed and configured locally

## Step 1: Set Up GitHub Repository Secrets

### Option 1: Using GitHub Web Interface (Recommended)

1. Open your GitHub repository: https://github.com/SwatiKasina/tax-service-api
2. Click on **Settings** tab
3. In the left sidebar, click **Secrets and variables**
4. Click **Actions**
5. Click **New repository secret**

#### Add AWS_ACCESS_KEY_ID:

- **Name**: `AWS_ACCESS_KEY_ID`
- **Value**: Your AWS access key ID (20-character alphanumeric string)
- **How to get it**: AWS Console → IAM → Users → Your User → Security credentials tab → Access keys → Create access key (if needed)
- Click **Add secret**

#### Add AWS_SECRET_ACCESS_KEY:

- **Name**: `AWS_SECRET_ACCESS_KEY`
- **Value**: Your AWS secret access key (40-character string)
- **How to get it from the same access key creation**
- Click **Add secret**

**Important**: Use an IAM user with the following permissions:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "codepipeline:StartPipelineExecution",
        "codepipeline:GetPipelineExecution",
        "codepipeline:ListPipelineExecutions"
      ],
      "Resource": "*"
    }
  ]
}
```

### Option 2: Using AWS CLI to Create IAM User

```bash
# Create IAM user for GitHub Actions
aws iam create-user --user-name github-actions-tax-api

# Create access key
aws iam create-access-key --user-name github-actions-tax-api

# Attach inline policy
aws iam put-user-policy --user-name github-actions-tax-api --policy-name GitHubActionsCodePipelineAccess --policy-document '{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "codepipeline:StartPipelineExecution",
        "codepipeline:GetPipelineExecution",
        "codepipeline:ListPipelineExecutions"
      ],
      "Resource": "*"
    }
  ]
}'
```

## Step 2: Create CodeStar GitHub Connection

1. Go to AWS Console → Developer Tools → Settings → Connections
2. Click **Create connection**
3. Choose **GitHub** as provider
4. Click **Connect to GitHub** and authorize AWS to access your GitHub account
5. Click **Install a new app** if prompted
6. Select your repository
7. Complete the connection setup
8. Note the **Connection ARN** (starts with `arn:aws:codeconnections:us-east-1:123456789012:connection/...`)
9. Update this ARN in `setup-pipeline.sh`:
   ```bash
   GITHUB_CONNECTION_ARN="YOUR_ACTUAL_ARN_HERE"
   ```

## Step 3: Create S3 Bucket for Artifacts

```bash
aws s3 mb s3://tax-service-api-$(aws sts get-caller-identity --query Account --output text)-artifacts --region us-east-1
```

## Step 4: Deploy Pipeline Infrastructure

Deploy the pipeline using CloudFormation:

```bash
aws cloudformation deploy \
  --template-file pipeline-infrastructure.yml \
  --stack-name tax-service-api-pipeline \
  --parameter-overrides GitHubConnectionArn=YOUR_CONNECTION_ARN \
  --capabilities CAPABILITY_IAM
```

## Step 5: Configure AWS IAM Roles (if needed)

### CodePipeline Service Role

If the script fails on role creation, create manually:

1. AWS Console → IAM → Roles → Create role
2. Trusted entity: AWS service → CodePipeline
3. Attach `AWSCodePipeline_FullAccess` policy
4. Name: `AWSCodePipelineServiceRole`

### CodeBuild Service Role

The CloudFormation template creates this, but ensure it exists before running pipeline.

## Step 6: Test the Pipeline

1. Push this code to your `main` branch:

   ```bash
   git add .
   git commit -m "Add CI/CD pipeline setup"
   git push origin main
   ```

2. Check GitHub Actions tab - the workflow should trigger
3. Monitor AWS CodePipeline console for deployment
4. Once complete, get the ALB URL from CloudFormation outputs

## Architecture Overview

- **GitHub Actions** → Triggers CodePipeline on push
- **CodePipeline Stages**:
  1. **Source**: Pull code from GitHub
  2. **Build**: Build Docker image with CodeBuild, push to ECR
  3. **Deploy Infrastructure**: Create VPC, ECS, ALB via CloudFormation
  4. **Deploy Service**: Deploy container to ECS with new image

## High Availability Features

- Multi-AZ deployment (us-east-1a, us-east-1b)
- Auto-scaling (0-3 tasks based on CPU utilization)
- Application Load Balancer with health checks
- Fargate for serverless container management

## Monitoring

- CloudWatch Logs: `/ecs/tax-service-api-prod`
- ALB access logs
- ECS service metrics and alarms

## Troubleshooting

1. **Pipeline fails at Deploy stage**: Check CloudFormation stack events
2. **Health checks fail**: Ensure Spring Boot Actuator is enabled (/actuator/health endpoint)
3. **ECR access denied**: Verify CodeBuild service role has ECR permissions
4. **GitHub connection fails**: Ensure CodeStar connection is in "Available" status

## Additional Database Setup

If using PostgreSQL RDS:

1. Add RDS stack to CloudFormation
2. Update task definition with database environment variables
3. Ensure VPC security groups allow ECS to RDS

The service will be accessible via the ALB DNS name (HTTP only, as requested).
