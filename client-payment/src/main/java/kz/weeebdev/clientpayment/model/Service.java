package kz.weeebdev.clientpayment.model;

import kz.weeebdev.clientpayment.model.enums.ServiceTypes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Service {

    private ServiceTypes serviceType;

    private double cost;

}
