package com.rodrigolazoti.commons.models;

import java.math.BigDecimal;

public class ProductDTO {

  private final Long id;
  private final Long storeId;
  private final String name;
  private final String description;
  private final BigDecimal price;

  public ProductDTO(Long id, Long storeId, String name, String description, BigDecimal price) {
    super();
    this.id = id;
    this.storeId = storeId;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public Long getStoreId() {
    return storeId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getPrice() {
    return price;
  }

}
