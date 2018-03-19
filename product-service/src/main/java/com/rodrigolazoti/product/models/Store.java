package com.rodrigolazoti.product.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "stores")
public class Store {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank
  private String name;

  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
  private List<Product> products;

  protected Store() {}

  public Store(Long id, String name) {
    this(name, null);
    this.id = id;
  }

  public Store(String name) {
    this(name, null);
  }

  public Store(String name, List<Product> products) {
    this.name = name;
    this.products = products == null ? new ArrayList<>() : products;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void addProduct(Product product) {
    products.add(product);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(id)
        .append(name)
        .append(products)
        .toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Store) {
      final Store other = (Store) obj;
      return new EqualsBuilder()
          .append(id, other.id)
          .append(name, other.name)
          .append(products, other.products)
          .isEquals();
    }
    else return false;
  }

  @Override
  public String toString() {
    return "Store [id=" + id + ", name=" + name + ", products=" + products + "]";
  }

}
