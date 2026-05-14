package com.apps.raeurekaserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RaEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RaEurekaServerApplication.class, args);
    }

}
