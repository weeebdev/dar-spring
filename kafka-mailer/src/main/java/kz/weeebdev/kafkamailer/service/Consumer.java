package kz.weeebdev.kafkamailer.service;

import kz.weeebdev.clientpayment.model.ClientPaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics = "random_topic", groupId = "group_id", containerFactory = "kafkaJsonListenerContainerFactory")
    public void consumeMessage(ClientPaymentResponse clientPaymentResponse) {
        String subject = String.format("We've received your payment %s", clientPaymentResponse.getPaymentId());

        String body = String.format("Hello, dear %s!\n You paid for services like: %s\n And total cost is %.2f",
                clientPaymentResponse.getClient().getName(),
                clientPaymentResponse.getServices(),
                clientPaymentResponse.getTotal());

        sendSimpleEmail(clientPaymentResponse.getClient().getEmail(), body, subject);
    }




    public void sendSimpleEmail(String email, String body, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("xoyog90318@decorbuz.com");
        message.setTo(email);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Message sent");
    }
}
