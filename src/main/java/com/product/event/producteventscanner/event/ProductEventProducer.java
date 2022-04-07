package com.product.event.producteventscanner.event;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.event.producteventscanner.domain.ProductScannerEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class ProductEventProducer {


    @Autowired
    private KafkaTemplate<Long, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void sendProductEvent(ProductScannerEvent productScannerEvent) throws JsonProcessingException {

        Long key = productScannerEvent.getProductEventId();
        String value = objectMapper.writeValueAsString(productScannerEvent.getProduct());

        ListenableFuture<SendResult<Long, String>>  listenableFuture = kafkaTemplate.sendDefault(key, value);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Long, String>>() {
            @Override
            public void onFailure(Throwable ex) {

            }
            @Override
            public void onSuccess(SendResult<Long, String> result) {
                handleSuccess(key, value, result);
            }
        });
    }



    public void sendProductEventUsingProducerRecord(ProductScannerEvent productScannerEvent) throws JsonProcessingException {

        Long key = productScannerEvent.getProductEventId();
        String value = objectMapper.writeValueAsString(productScannerEvent.getProduct());

        ProducerRecord<Long, String> producerRecord = new ProducerRecord<Long, String>("test-topic", null, key, value, null);
        ListenableFuture<SendResult<Long, String>>  listenableFuture = kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Long, String>>() {
            @Override
            public void onFailure(Throwable ex) {

            }
            @Override
            public void onSuccess(SendResult<Long, String> result) {
                handleSuccess(key, value, result);
            }
        });


    }
    public void handleSuccess(Long key, String value, SendResult<Long, String> result) {
        System.out.println("Successfully posted the message to QUEUE");
    }
}
