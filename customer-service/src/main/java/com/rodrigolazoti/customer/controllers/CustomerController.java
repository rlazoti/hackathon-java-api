package com.rodrigolazoti.customer.controllers;

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
import com.rodrigolazoti.commons.models.CustomerDTO;
import com.rodrigolazoti.customer.models.Customer;
import com.rodrigolazoti.customer.repositories.CustomerRepository;

@RestController
@RequestMapping("/api/v1/Customer")
public class CustomerController {

  @Autowired
  private CustomerRepository customerRepository;

  /**
   * Returns 201 and the new customer if operation is successful or 400 if the data is invalid
   * @param customer Customer data
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Customer create(@RequestBody @Valid Customer customer) {
    return customerRepository.save(customer);
  }

  /**
   * Returns a customer based on its ID with 200 HTTP status or 404 in case no customer was found
   * @param id Product ID 
   */
  @GetMapping("/{id}")
  public CustomerDTO findById(@PathVariable("id") Long id) {
    return customerRepository
        .findById(id)
        .map(Customer::toDTO)
        .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
  }

}
