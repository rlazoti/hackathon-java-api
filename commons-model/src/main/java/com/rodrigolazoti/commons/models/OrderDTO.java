package com.rodrigolazoti.commons.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDTO {

  private final Long id;
  private final Date creation;
  private final Long customerId;
  private final Long storeId;
  private final List<OrderItemDTO> orderItems;
  private final String deliveryAddress;
  private final String contact;
  private final BigDecimal total;

  public OrderDTO(Long id, Date creation, Long customerId, Long storeId, 
      List<OrderItemDTO> orderItems, String deliveryAddress, String contact, BigDecimal total) {

    super();
    this.id = id;
    this.creation = creation;
    this.customerId = customerId;
    this.storeId = storeId;
    this.orderItems = orderItems;
    this.deliveryAddress = deliveryAddress;
    this.contact = contact;
    this.total = total;
  }

  public Long getId() {
    return id;
  }

  public Date getCreation() {
    return creation;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public Long getStoreId() {
    return storeId;
  }

  public List<OrderItemDTO> getOrderItems() {
    return orderItems;
  }

  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public String getContact() {
    return contact;
  }

  public BigDecimal getTotal() {
    return total;
  }

}
