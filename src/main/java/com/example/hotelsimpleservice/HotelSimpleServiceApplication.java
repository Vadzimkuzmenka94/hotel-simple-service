package com.example.hotelsimpleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.Ordered;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication(scanBasePackages = "com.example.hotelsimpleservice")
@EntityScan(basePackages = "com.example.hotelsimpleservice.model")
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class HotelSimpleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelSimpleServiceApplication.class, args);
    }
}