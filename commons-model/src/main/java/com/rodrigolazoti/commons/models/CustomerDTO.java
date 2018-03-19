package com.rodrigolazoti.commons.models;

import java.util.Date;

public class CustomerDTO {

  private final Long id;

  private final String name;

  private final String email;

  private final String address;

  private final Date creation;

  public CustomerDTO(Long id, String name, String email, String address, Date creation) {
    super();
    this.id = id;
    this.name = name;
    this.email = email;
    this.address = address;
    this.creation = creation;
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

}
