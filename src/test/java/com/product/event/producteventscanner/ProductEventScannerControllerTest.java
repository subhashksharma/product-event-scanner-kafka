package com.product.event.producteventscanner;


import com.product.event.producteventscanner.domain.Product;
import com.product.event.producteventscanner.domain.ProductScannerEvent;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = "test-topic")
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}"})

public class ProductEventScannerControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;


    private Consumer<Long, String> consumer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @BeforeEach
    void setup() {
        Map<String, Object> map = new HashMap<>(KafkaTestUtils.consumerProps("groups", "true",
                embeddedKafkaBroker ));
        consumer = new DefaultKafkaConsumerFactory<>(map, new LongDeserializer(), new StringDeserializer()).createConsumer();
        embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer);
    }
    @Test
    void postProductEvent() {

        Product product = Product.builder()
                .productId(1L)
                .productName("ALGO BOOK")
                .build();
        ProductScannerEvent  productScannerEvent = ProductScannerEvent.builder()
                        .product(product)
                        .build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<ProductScannerEvent> request =  new HttpEntity<>(productScannerEvent, httpHeaders);
       ResponseEntity<ProductScannerEvent> productEvent =  testRestTemplate.exchange("/v1/productevent", HttpMethod.POST, request, ProductScannerEvent.class);
       assertEquals(HttpStatus.CREATED, productEvent.getStatusCode());

      ConsumerRecord<Long, String > consumerRecord=  KafkaTestUtils.getSingleRecord(consumer, "test-topic");

     // System.out.println(consumerRecord.value().toString());
    }
}
