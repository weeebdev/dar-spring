package kz.weeebdev.kafkamailer.service;

import kz.weeebdev.clientpayment.model.ClientPaymentResponse;
import kz.weeebdev.kafkamailer.model.EmployeeEntity;
import kz.weeebdev.kafkamailer.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class Consumer {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmployeeRepository employeeRepository;

    @KafkaListener(topics = "random_topic", groupId = "group_id", containerFactory = "kafkaJsonListenerContainerFactory")
    public void consumeMessage(ClientPaymentResponse clientPaymentResponse) {
        notifyClient(clientPaymentResponse);
        notifyEmployees(clientPaymentResponse);
    }

    public void notifyEmployees(ClientPaymentResponse clientPaymentResponse) {
        String subject = String.format("New payment has been received %s", clientPaymentResponse.getPaymentId());

        String body = String.format("%s paid for services like: %s\n And total cost is %.2f",
                clientPaymentResponse.getClient().getName(),
                clientPaymentResponse.getServices(),
                clientPaymentResponse.getTotal());

        List<EmployeeEntity> employees = employeeRepository.findAll();

        List<String> employeeEmails = StreamSupport.stream(employees.spliterator(), false)
                .map(employee -> employee.getEmail()).collect(Collectors.toList());

        sendSimpleEmail(body, subject, employeeEmails.toArray(new String[]{}));
    }

    public void notifyClient(ClientPaymentResponse clientPaymentResponse) {
        String subject = String.format("We've received your payment %s", clientPaymentResponse.getPaymentId());

        String body = String.format("Hello, dear %s!\n You paid for services like: %s\n And total cost is %.2f",
                clientPaymentResponse.getClient().getName(),
                clientPaymentResponse.getServices(),
                clientPaymentResponse.getTotal());

        sendSimpleEmail(body, subject, clientPaymentResponse.getClient().getEmail());
    }


    public void sendSimpleEmail(String body, String subject, String... email) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("xoyog90318@decorbuz.com");
        message.setTo(email);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Message sent");
    }
}
