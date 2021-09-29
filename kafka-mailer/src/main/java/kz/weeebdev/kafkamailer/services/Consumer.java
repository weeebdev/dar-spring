package kz.weeebdev.kafkamailer.services;

import kz.weeebdev.clientpayment.model.ClientPaymentResponse;
import kz.weeebdev.clientpayment.model.ClientResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    @KafkaListener(topics = "random_topic", groupId = "group_id", containerFactory = "kafkaJsonListenerContainerFactory")
    public void consumeMessage(ClientPaymentResponse clientPaymentResponse) {
        System.out.println(clientPaymentResponse.getServices());
    }
}
