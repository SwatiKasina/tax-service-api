# Tax Service API - Post-Deploy Verification Guide

This repository contains the backend for the Tax Service API (author: Swati). The instructions below help you verify that a deployment completed successfully using the AWS Console and a few local commands (no CloudShell required).

## What the pipeline deploys

- Network (optional) → ALB → ECS Infrastructure (Task Definition, Service, SG, Logs) → Build → Deploy Service → DNS (Route 53)
- Stacks:
  - tax-service-api-prod-alb
  - tax-service-api-prod-ecs-service
  - tax-service-api-prod-dns (if enabled)

## 1) Verify ECS service

Console path:

- ECS → Clusters → `tax-service-api-prod-cluster` → Services → `tax-service-api-prod-service`

Check:

- Status: Active
- Desired tasks: 1 (if 0, update service to Desired=1)
- Running tasks: 1
- Events: Should show Successful service deployment, steady state reached.

If Desired is 0:

- Click “Update” on the service → set Desired tasks = 1 → Update service.

## 2) Verify Target Group health (ALB)

Console path:

- EC2 → Target Groups → `tax-service-api-prod-tg` → Targets tab

Check:

- You should see at least 1 target with Health = healthy.
- If “No targets” → ECS Service Desired is likely 0.
- If “Unhealthy” → click a target’s “Health status details” to see the reason (e.g., connection refused, 5xx, timeout).

## 3) Verify ALB listeners

Console path:

- EC2 → Load Balancers → `tax-service-api-prod-alb` → Listeners tab

Check:

- HTTP (80) listener redirects to HTTPS (443)
- HTTPS (443) listener forwards to the target group `tax-service-api-prod-tg`
- Security Group for the ALB allows 80/443 from 0.0.0.0/0 (inbound).

## 4) Verify DNS (Route 53)

Console path:

- Route 53 → Hosted zones → `tkpshivatemple.com`
- Record name: `api.tkpshivatemple.com` (Type A — Alias to Application Load Balancer) or CNAME to ALB DNS

Check:

- Record should exist and point to the ALB
- Health checks are not required for alias but are okay if enabled

Local tests (from your machine/terminal):

- Resolve the name:
  - macOS/Linux: `dig +short api.tkpshivatemple.com`
  - Windows PowerShell: `Resolve-DnsName api.tkpshivatemple.com`
- Quick TLS and HTTP checks:
  - `curl -I https://api.tkpshivatemple.com/actuator/health/liveness`
  - `openssl s_client -connect api.tkpshivatemple.com:443 -servername api.tkpshivatemple.com -showcerts | head -n 20`

Expected:

- `curl` should return HTTP/1.1 200 and a small JSON body `{"status":"UP"}` if you hit the full URL without -I.

## 5) Verify application logs

Console path:

- CloudWatch Logs → Log groups → `/ecs/tax-service-api-prod-tax-service-api-prod-ecs-service`

Check:

- Recent log stream shows Spring Boot application start without errors
- Health/liveness endpoints are being hit by ALB/container health checks

If you don’t see logs:

- Ensure there is at least one running task in the ECS service
- The log group name includes the ECS service stack name; search for prefix `/ecs/tax-service-api-prod-` in CloudWatch Logs

## 6) Common issues and quick fixes

- 503 from ALB:
  - Usually “No healthy targets”. Ensure Desired=1; wait for Running=1. Check Target Group “Health status details”.
- Container Unhealthy:
  - The application may be failing on DB init or not listening on port 8080.
  - This project enables liveness probes and prevents DB fail-fast at startup in `application.properties` so the app can boot and pass health checks even if DB is briefly unavailable.
- DNS not resolving yet:
  - Route 53 changes can take a few minutes; verify that the record exists and points to your ALB.
- HTTPS certificate:
  - Ensure the ACM certificate for `api.tkpshivatemple.com` is in the same region as the ALB and attached to the 443 listener.

## 7) Roll forward / Scale

- To increase capacity, update the service `Desired tasks` to 2+ and confirm multiple healthy targets appear.
- Auto Scaling is configured (min 0, max 3); you can tune the target CPU utilization as needed in the ECS infra stack.

## 8) Where to adjust Infra

- ALB template: `alb-infrastructure.yml`
  - Optional `UseExistingNetwork=true` and `ExistingVpc/Subnet` parameters let you deploy into an existing VPC (e.g., where RDS resides).
- ECS template: `ecs-service-infrastructure.yml`
  - Optional `RdsSecurityGroupId` lets you authorize the ECS service SG to the RDS SG on 5432.
  - `DesiredCount` sets the number of running tasks initially.

## 9) When to contact logs

Provide the following for quick triage:

- ECS service desired/running counts
- Target Group Health status details
- Last 100 lines from the CloudWatch log stream for the container
- Any recent ALB listener or CFN stack errors

---

This guide avoids AWS CloudShell and focuses on Console navigation and local commands you can run on macOS/Linux/Windows. If you prefer CLI-based workflows in the future, a separate section can be added with those commands.
