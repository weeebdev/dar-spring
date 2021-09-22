package kz.weeebdev.clientpayments.feign;

import kz.weeebdev.clientpayments.model.ClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "client-api")
public interface ClientFeign {
    @GetMapping("/client/healthCheck")
    public String healthCheck();

    @GetMapping("/client/")
    public List<ClientResponse> getAllClients();

    @GetMapping("/client/{id}")
    public ClientResponse getClient(@PathVariable String id);
}
