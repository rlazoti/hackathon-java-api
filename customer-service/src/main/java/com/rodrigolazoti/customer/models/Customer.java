package com.rodrigolazoti.customer.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rodrigolazoti.commons.models.CustomerDTO;

@Entity
@Table(name = "customers")
@JsonIgnoreProperties(value = { "creation" }, allowGetters = true)
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String address;

  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  private Date creation;

  @NotBlank
  private String password;

  protected Customer() {}

  public Customer(String name, String email, String address, String password) {
    this.name = name;
    this.email = email;
    this.address = address;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getAddress() {
    return address;
  }

  public Date getCreation() {
    return creation;
  }

  public String getPassword() {
    return password;
  }

  public CustomerDTO toDTO() {
    return new CustomerDTO(id, name, email, address, creation);
  }

  @Override
  public String toString() {
    return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address
        + ", creation=" + creation + ", password=" + password + "]";
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(id)
        .append(name)
        .append(email)
        .append(address)
        .append(creation)
        .append(password)
        .toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Customer) {
      final Customer other = (Customer) obj;
      return new EqualsBuilder()
          .append(id, other.id)
          .append(name, other.name)
          .append(email, other.email)
          .append(address, other.address)
          .append(creation, other.creation)
          .append(password, other.password)
          .isEquals();
    }
    else return false;
  }

}
