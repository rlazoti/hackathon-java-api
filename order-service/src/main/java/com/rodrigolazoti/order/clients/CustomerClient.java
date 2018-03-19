package com.rodrigolazoti.order.clients;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.rodrigolazoti.commons.models.CustomerDTO;

@Component
public class CustomerClient {

  @Autowired
  private RestTemplate restTemplate;

  private final String SERVICE_URL = "http://customer-service/api/v1/Customer";

  public CustomerClient() {
    restTemplate = new RestTemplate();
  }

  public Optional<CustomerDTO> findById(Long id) {
    return Optional.ofNullable(
        restTemplate.getForObject(String.format("%s/%d", SERVICE_URL, id), 
            CustomerDTO.class));
  }

}
