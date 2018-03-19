package com.rodrigolazoti.product.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigolazoti.commons.exceptions.ResourceNotFoundException;
import com.rodrigolazoti.commons.models.ProductDTO;
import com.rodrigolazoti.product.models.Product;
import com.rodrigolazoti.product.repositories.ProductRepository;

@RestController
@RequestMapping("/api/v1/Product")
public class ProductController {

  @Autowired
  private ProductRepository productRepository;

  /**
   * Returns all available products with 200 HTTP status 
   */
  @GetMapping()
  public List<ProductDTO> all() {
    return productRepository
        .findAll()
        .stream()
        .map(Product::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * Returns a products based on its ID with 200 HTTP status or 404 in case no product was found
   * @param id Product ID 
   */
  @GetMapping("/{id}")
  public ProductDTO findById(@PathVariable("id") Long id) {
    return productRepository
        .findById(id)
        .map(Product::toDTO)
        .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
  }

  /**
   * Search for products that contains some text in their names and return it with 200 HTTP status
   * @param text Text used on the search
   */
  @GetMapping("/search/{text}")
  public List<ProductDTO> search(@PathVariable("text") String text) {
    return productRepository
        .findByNameContainingIgnoreCase(text)
        .stream()
        .map(Product::toDTO)
        .collect(Collectors.toList());
  }

}
