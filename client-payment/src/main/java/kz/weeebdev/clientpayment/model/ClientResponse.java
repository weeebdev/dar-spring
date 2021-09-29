package kz.weeebdev.clientpayment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponse {

    private String clientId;

    private String name;

    private String email;
}