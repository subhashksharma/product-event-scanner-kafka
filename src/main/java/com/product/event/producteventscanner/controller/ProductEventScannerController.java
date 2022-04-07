package com.product.event.producteventscanner.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.product.event.producteventscanner.domain.ProductScannerEvent;
import com.product.event.producteventscanner.event.ProductEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ProductEventScannerController {

    @Autowired
    private ProductEventProducer productEventProducer;

    @PostMapping("/v1/productevent")
    public ResponseEntity<ProductScannerEvent> saveProductEvent( @RequestBody  @Valid ProductScannerEvent productScannerEvent) throws JsonProcessingException {

        //productEventProducer.sendProductEvent(productScannerEvent);

        productEventProducer.sendProductEventUsingProducerRecord(productScannerEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(productScannerEvent);
    }
}
