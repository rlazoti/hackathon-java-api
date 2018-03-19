package com.rodrigolazoti.order.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigolazoti.commons.exceptions.ResourceNotFoundException;
import com.rodrigolazoti.commons.models.OrderDTO;
import com.rodrigolazoti.order.clients.CustomerClient;
import com.rodrigolazoti.order.models.Order;
import com.rodrigolazoti.order.repositories.OrderRepository;

@RestController
@RequestMapping("/api/v1/Order")
public class OrderController {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private CustomerClient customerRepository;

  /**
   * Returns an order based on its ID with 200 HTTP status or 404 in case no order was found
   * @param id Order ID 
   * @return
   */
  @GetMapping("/{id}")
  public OrderDTO findById(@PathVariable("id") Long id) {
    return orderRepository
        .findById(id)
        .map(Order::toDTO)
        .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
  }

  /**
   * Returns HTTP status 201 and new entity if operation successful
   * HTTP status 400 if invalid data supplied
   * HTTP status 404 if any of the following models are invalid: Customer, Store, Product
   * @param customer
   * @return
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Order create(@RequestBody @Valid Order order) {
    customerRepository
      .findById(order.getCustomerId())
      .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", order.getCustomerId()));

    return orderRepository.save(order);
  }

}
