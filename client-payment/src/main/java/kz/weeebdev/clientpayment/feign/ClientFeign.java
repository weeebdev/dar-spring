package kz.weeebdev.clientpayment.feign;

import kz.weeebdev.clientpayment.model.ClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client-api")
public interface ClientFeign {
    @GetMapping("/client/healthCheck")
    public String healthCheck();

    @GetMapping("client/{id}")
    public ClientResponse getClient(@PathVariable String id);
}
