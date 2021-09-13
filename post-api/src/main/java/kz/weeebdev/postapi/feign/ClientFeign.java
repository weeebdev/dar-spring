package kz.weeebdev.postapi.feign;

import kz.weeebdev.clientapi.model.ClientModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "client-api")
public interface ClientFeign {
    @GetMapping("/client/healthCheck")
    public String healthCheck();

    @GetMapping("/client/")
    public List<ClientModel> getAllClients();

    @GetMapping("/client/{id}")
    public ClientModel getClient(@PathVariable String id);
}
