package org.clairvoyant.modelmapper.functional.disambiguation;

import static org.testng.Assert.assertEquals;

import org.clairvoyant.modelmapper.AbstractTest;
import org.testng.annotations.Test;

/**
 * @author Jonathan Halterman
 */
@Test(groups = "functional")
public class DisambiguationTest1 extends AbstractTest {
  public static class Source {
    SourceInner value = new SourceInner();
  }

  public static class SourceInner {
    String valueInner = "abc";
  }

  public static class Dest {
    DestInner value;
  }

  public static class DestInner {
    String valueInner;
  }

  /**
   * value/valueInner -> value/valueInner
   */
  public void shouldDisambiguate() {
    Dest dest = modelMapper.map(new Source(), Dest.class);
    modelMapper.validate();
    assertEquals(dest.value.valueInner, "abc");
  }
}
