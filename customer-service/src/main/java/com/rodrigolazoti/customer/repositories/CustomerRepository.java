package com.rodrigolazoti.customer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigolazoti.customer.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
