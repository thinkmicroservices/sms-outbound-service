package com.thinkmicroservices.ri.spring.sms.outbound;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *
 * @author cwoodward
 */
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class OutboundSmsApplication {

    @Value("${configuration.source:DEFAULT}")
    String configSource;
    @Value("${spring.application.name:NOT-SET}")
    private String serviceName;

    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        SpringApplication.run(OutboundSmsApplication.class, args);

    }

    /**
     * 
     */
    @PostConstruct
    private void displayInfo() {
        log.info("Service-Name:{}, configuration.source={}", serviceName, configSource);
    }
}
