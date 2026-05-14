package com.apps.forecastservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
public class ForecastServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForecastServiceApplication.class, args);
      /*  ForecastAPIClient apiClient = new ForecastAPIClient();
        try {
            System.out.println(apiClient.getWeatherData());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }*/
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
