package io.fourfinance.pos.dataprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@EnableDiscoveryClient
public class Main {

    public static void main(String... args) {
        SpringApplication.run(Main.class, args);
    }
}
