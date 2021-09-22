package kz.weeebdev.clientpayments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ClientPaymentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientPaymentsApplication.class, args);
    }

}
