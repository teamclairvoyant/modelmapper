package org.clairvoyant.modelmapper.projection.example2;

public class OrderDTO {
  private String street;
  String city;

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }
}
