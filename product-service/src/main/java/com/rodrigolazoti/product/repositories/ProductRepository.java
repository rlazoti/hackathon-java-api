package com.rodrigolazoti.product.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigolazoti.product.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findByNameContainingIgnoreCase(String name);

}
