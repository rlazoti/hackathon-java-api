package com.rodrigolazoti.product.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.rodrigolazoti.commons.models.ProductDTO;

@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "store_id")
  private Store store;

  @NotBlank
  private String name;

  private String description;

  @NotNull
  @NumberFormat(style=Style.CURRENCY)
  private BigDecimal price;

  protected Product() {}

  public Product(String name, Store store, String description, BigDecimal price) {
    this.name = name;
    this.store = store;
    this.description = description;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public Store getStore() {
    return store;
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

  public ProductDTO toDTO() {
    return new ProductDTO(id, store != null ? store.getId() : null, name, description, price);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(id)
        .append(name)
        .append(store)
        .append(description)
        .append(price)
        .toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Product) {
      final Product other = (Product) obj;
      return new EqualsBuilder()
          .append(id, other.id)
          .append(name, other.name)
          .append(store, other.store)
          .append(description, other.description)
          .append(price, other.price)
          .isEquals();
    }
    else return false;
  }

  @Override
  public String toString() {
    return "Product [id=" + id + ", name=" + name + ", description="
        + description + ", price=" + price + "]";
  }

}
