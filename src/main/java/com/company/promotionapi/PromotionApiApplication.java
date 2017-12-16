package com.company.promotionapi;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class PromotionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromotionApiApplication.class, args);
    }
}
