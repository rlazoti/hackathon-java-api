package com.rodrigolazoti.product.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigolazoti.commons.exceptions.ResourceNotFoundException;
import com.rodrigolazoti.product.models.Product;
import com.rodrigolazoti.product.models.Store;
import com.rodrigolazoti.product.repositories.StoreRepository;

@RestController
@RequestMapping("/api/v1/Store")
public class StoreController {

  @Autowired
  private StoreRepository storeRepository;

  /**
   * Returns all available stores with 200 HTTP status 
   */
  @GetMapping()
  public List<Store> all() {
    return storeRepository.findAll();
  }

  /**
   * Returns a products based on its ID with 200 HTTP status or 404 in case no product was found
   * @param id Product ID 
   */
  @GetMapping("/{id}")
  public Store findById(@PathVariable("id") Long id) {
    return storeRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Store", "id", id));
  }

  /**
   * Search for stores that contains some text in their names and return it with 200 HTTP status
   * @param text Text used on the search
   */
  @GetMapping("/search/{text}")
  public List<Store> search(@PathVariable("text") String text) {
    return storeRepository.findByNameContainingIgnoreCase(text);
  }

  /**
   * Returns all products of an store, if the store does not exists it returns 404 HTTP status
   * @param id Store ID
   */
  @GetMapping("/{id}/products")
  public List<Product> findProductsByStoreId(@PathVariable("id") Long id) {
    return storeRepository
        .findById(id)
        .map(Store::getProducts)
        .orElseThrow(() -> new ResourceNotFoundException("Store", "id", id));
  }

}
