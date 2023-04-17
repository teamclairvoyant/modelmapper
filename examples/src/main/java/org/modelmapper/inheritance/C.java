package org.clairvoyant.modelmapper.inheritance;

import java.util.List;

public class C {

  private List<BaseSrc> bases;

  public C(List<BaseSrc> bases) {
    this.bases = bases;
  }

  public List<BaseSrc> getBases() {
    return bases;
  }
}
