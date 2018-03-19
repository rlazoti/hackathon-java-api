package com.rodrigolazoti.order;

import java.math.BigDecimal;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.rodrigolazoti.order.models.Order;
import com.rodrigolazoti.order.models.OrderItem;
import com.rodrigolazoti.order.repositories.OrderRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class Application {

  @Autowired
  private OrderRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  InitializingBean sendDatabase() {
    return () -> {
      Order order = new Order(1L, 1L, "address ST 123", "Rodrigo");
      OrderItem item1 = new OrderItem(1L, order, new BigDecimal(2.99), 3L);
      OrderItem item2 = new OrderItem(2L, order, new BigDecimal(4.50), 2L);

      order.addOrderItem(item1);
      order.addOrderItem(item2);

      repository.save(order);
    };
  }

}
