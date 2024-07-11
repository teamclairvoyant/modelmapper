package org.clairvoyant.modelmapper.functional.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Employee {
  @GeneratedValue @Id public long id;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}