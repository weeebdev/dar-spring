package kz.weeebdev.clientapi.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "post-api")
public interface PostFeign {
    @GetMapping("/post/healthCheck")
    public String healthCheck();
}
