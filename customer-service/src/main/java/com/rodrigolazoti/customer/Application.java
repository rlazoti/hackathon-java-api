package com.rodrigolazoti.customer;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.rodrigolazoti.customer.models.Customer;
import com.rodrigolazoti.customer.repositories.CustomerRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class Application {

  @Autowired
  private CustomerRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  InitializingBean sendDatabase() {
    return () -> {
      repository.save(new Customer("rodrigo", "rodrigolazoti@gmail.com", "street 1", "outch"));
    };
  }

}
