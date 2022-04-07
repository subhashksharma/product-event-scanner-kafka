package com.product.event.producteventscanner;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.event.producteventscanner.controller.ProductEventScannerController;
import com.product.event.producteventscanner.domain.Product;
import com.product.event.producteventscanner.domain.ProductScannerEvent;
import com.product.event.producteventscanner.event.ProductEventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductEventScannerController.class)
@AutoConfigureMockMvc
public class ProductEventScannerJUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductEventProducer productEventProducer;

    ObjectMapper  objectMapper  =  new ObjectMapper();


    @Test
    void saveEvent() throws Exception {

        Product product = Product.builder()
                .productId(1L)
                .productName("ALGO")
                .build();

        ProductScannerEvent  productScannerEvent = ProductScannerEvent
                .builder()
                .product(product)
                .build();

        String json = objectMapper.writeValueAsString(productScannerEvent);

        mockMvc.perform(post("/v1/productevent")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated());

    }


    @Test
    void saveEvent_4XX() throws Exception {

        Product product = Product.builder()
                .productId(null)
                .productName("ALGO")
                .build();

        ProductScannerEvent  productScannerEvent = ProductScannerEvent
                .builder()
                .product(product)
                .build();

        String json = objectMapper.writeValueAsString(productScannerEvent);

        mockMvc.perform(post("/v1/productevent")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError());

    }
}
