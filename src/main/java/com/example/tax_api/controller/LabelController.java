package com.example.tax_api.controller;

import com.example.tax_api.model.ItemDetails;
import com.example.tax_api.model.LabelDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api")


public class LabelController {

private static final Map<String, String> labelsMap = new HashMap<>();


    @PostMapping(
            value = "/labels",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<LabelDetails> createLabel(@RequestBody LabelDetails lb) {

        if (lb.getKey() == null || lb.getValue() == null) {
            return ResponseEntity.badRequest().build();
        }

        labelsMap.put(lb.getKey(), lb.getValue());

        return ResponseEntity.status(HttpStatus.CREATED).body(lb);

    }


    @GetMapping("/labels/{key}")
    public ResponseEntity<LabelDetails> getLabels(@PathVariable String key) {

        if(!labelsMap.containsKey(key)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        LabelDetails labelDetails = new LabelDetails();
        labelDetails.setKey(key);
        labelDetails.setValue(labelsMap.get(key));

        return ResponseEntity.ok(labelDetails);
    }


}
