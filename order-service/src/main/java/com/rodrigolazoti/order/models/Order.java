package com.rodrigolazoti.order.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rodrigolazoti.commons.models.OrderDTO;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties(value = { "creation" }, allowGetters = true)
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  private Date creation;

  @NotNull
  private Long customerId;

  @NotNull
  private Long storeId;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems;

  @NotBlank
  private String deliveryAddress;

  @NotBlank
  private String contact;

  @NotNull
  @NumberFormat(style = Style.CURRENCY)
  private BigDecimal total;

  @NotNull
  @Enumerated(value = EnumType.STRING)
  private OrderStatus status;

  protected Order() {}

  public Order(Long customerId, Long storeId, String deliveryAddress, String contact) {
    this.customerId = customerId;
    this.storeId = storeId;
    this.deliveryAddress = deliveryAddress;
    this.contact = contact;
    this.orderItems = new ArrayList<>();
    this.status = OrderStatus.PENDING;
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

  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public String getContact() {
    return contact;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void addOrderItem(OrderItem item) {
    orderItems.add(item);
    recalculateTotal();
  }

  private void recalculateTotal() {
    total = getOrderItems()
        .stream()
        .map(item -> item.getTotal())
        .reduce(new BigDecimal(0.00), (a, b) -> a.add(b))
        .setScale(2, RoundingMode.FLOOR);
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems == null ? new ArrayList<>() : orderItems;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public OrderDTO toDTO() {
    return new OrderDTO(id, creation, customerId, storeId,
        getOrderItems().stream().map(OrderItem::toDTO).collect(Collectors.toList()),
        deliveryAddress, contact, total);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(id)
        .append(creation)
        .append(customerId)
        .append(storeId)
        .append(orderItems)
        .append(deliveryAddress)
        .append(contact)
        .append(total)
        .append(status)
        .toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Order) {
      final Order other = (Order) obj;
      return new EqualsBuilder()
          .append(id, other.id)
          .append(creation, other.creation)
          .append(customerId, other.customerId)
          .append(storeId, other.storeId)
          .append(orderItems, other.orderItems)
          .append(deliveryAddress, other.deliveryAddress)
          .append(contact, other.contact)
          .append(total, other.total)
          .append(status, other.status)
          .isEquals();
    }
    else return false;
  }

  @Override
  public String toString() {
    return "Order [id=" + id + ", creation=" + creation + ", customerId=" + customerId
        + ", storeId=" + storeId + ", orderItems=" + orderItems + ", deliveryAddress="
        + deliveryAddress + ", contact=" + contact + ", total=" + total + ", status=" + status
        + "]";
  }

}
