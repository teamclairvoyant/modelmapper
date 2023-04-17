package org.clairvoyant.modelmapper.functional.deepmapping;

import static org.testng.Assert.assertEquals;

import org.clairvoyant.modelmapper.AbstractTest;
import org.clairvoyant.modelmapper.PropertyMap;
import org.clairvoyant.modelmapper.config.Configuration.AccessLevel;
import org.testng.annotations.Test;

@Test(groups = "functional")
public class NestedMappingTest8 extends AbstractTest {
  public static class Source {
    SourceInner value = new SourceInner();

    public SourceInner getValue() {
      return value;
    }
  }

  public static class Dest {
    DestInner value;

    void setValue(DestInner value) {
      this.value = value;
    }
  }

  public static class SourceInner {
    String valueinner = "abc";
  }

  public static class DestInner {
    String valueinner;
  }

  public void shouldMapToConstantSource() {
    modelMapper.getConfiguration().setMethodAccessLevel(AccessLevel.PUBLIC);
    final DestInner d = new DestInner();
    modelMapper.addMappings(new PropertyMap<Source, Dest>() {
      protected void configure() {
        map().setValue(d);
      }
    });

    Dest result = modelMapper.map(new Source(), Dest.class);
    assertEquals(result.value.valueinner, "abc");
    assertEquals(result.value, d);
  }
}
