package com.example.demo.client;

import com.example.demo.common.Utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("User-service")
public interface UserClient {

    @GetMapping("/users/getUser/{id}")
    Result getUser(@PathVariable Integer id);
}
