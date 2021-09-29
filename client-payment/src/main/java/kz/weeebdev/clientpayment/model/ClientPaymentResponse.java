package kz.weeebdev.clientpayment.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientPaymentResponse {
    private String paymentId;

    private ClientResponse client;

    private List<Service> services;

    private double total;

}
