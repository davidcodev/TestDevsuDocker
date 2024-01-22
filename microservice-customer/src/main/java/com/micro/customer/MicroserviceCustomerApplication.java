package com.micro.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient//cliente eureka
@SpringBootApplication
public class MicroserviceCustomerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicroserviceCustomerApplication.class, args);
	}
}
