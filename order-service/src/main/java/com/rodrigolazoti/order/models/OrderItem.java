package com.rodrigolazoti.order.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.rodrigolazoti.commons.models.OrderItemDTO;

@Entity
@Table(name = "order_items")
public class OrderItem {

  protected OrderItem() {}

  public OrderItem(Long productId, Order order, BigDecimal price, Long quantity) {
    this.productId = productId;
    this.order = order;
    this.price = price;
    this.quantity = quantity;
    this.total = price
        .setScale(2, RoundingMode.FLOOR)
        .multiply(new BigDecimal(quantity))
        .setScale(2, RoundingMode.FLOOR);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private Long productId;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @NotNull
  @NumberFormat(style=Style.CURRENCY)
  private BigDecimal price;

  @NotNull
  private Long quantity;

  @NotNull
  @NumberFormat(style=Style.CURRENCY)
  private BigDecimal total;

  public Long getId() {
    return id;
  }

  public Order getOrder() {
    return order;
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

  public OrderItemDTO toDTO() {
    return new OrderItemDTO(id, order.getId(), price, quantity, total, productId);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(id)
        .append(productId)
        .append(order)
        .append(price)
        .append(quantity)
        .append(total)
        .toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof OrderItem) {
      final OrderItem other = (OrderItem) obj;
      return new EqualsBuilder()
          .append(id, other.id)
          .append(productId, other.productId)
          .append(order, other.order)
          .append(price, other.price)
          .append(quantity, other.quantity)
          .append(total, other.total)
          .isEquals();
    }
    else return false;
  }

  @Override
  public String toString() {
    return "OrderItem [id=" + id + ", productId=" + productId + ", price="
        + price + ", quantity=" + quantity + ", total=" + total + "]";
  }

}
