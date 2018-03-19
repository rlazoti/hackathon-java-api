package com.rodrigolazoti.commons.models;

import java.math.BigDecimal;

public class OrderItemDTO {

  private Long id;
  private Long orderId;
  private BigDecimal price;
  private Long quantity;
  private BigDecimal total;
  private Long productId;

  public OrderItemDTO(Long id, Long orderId, BigDecimal price, Long quantity, BigDecimal total,
      Long productId) {

    super();
    this.id = id;
    this.orderId = orderId;
    this.price = price;
    this.quantity = quantity;
    this.total = total;
    this.productId = productId;
  }

  public Long getId() {
    return id;
  }

  public Long getOrderId() {
    return orderId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Long getQuantity() {
    return quantity;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public Long getProductId() {
    return productId;
  }

}
