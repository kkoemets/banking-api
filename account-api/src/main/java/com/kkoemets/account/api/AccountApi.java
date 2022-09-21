package com.kkoemets.account.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:application-context.xml"})
public class AccountApi {

    public static void main(String[] args) {
        SpringApplication.run(AccountApi.class, args);
    }

}
