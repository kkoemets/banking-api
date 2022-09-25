package com.kkoemets.daemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:application-context.xml"})
public class Daemon {

    public static void main(String[] args) {
        SpringApplication.run(Daemon.class, args);
    }

}
