package com.rodrigolazoti.product.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigolazoti.product.models.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
  List<Store> findByNameContainingIgnoreCase(String name);
}
