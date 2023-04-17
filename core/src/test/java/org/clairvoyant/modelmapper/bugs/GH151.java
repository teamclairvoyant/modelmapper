package org.clairvoyant.modelmapper.bugs;

import org.clairvoyant.modelmapper.AbstractTest;
import org.clairvoyant.modelmapper.Converter;
import org.clairvoyant.modelmapper.spi.MappingContext;
import org.testng.annotations.Test;

@Test
public class GH151 extends AbstractTest {
  static class Source {
    private int i;

    public Source(int i) {
      this.i = i;
    }

    public int getI() {
      return i;
    }

    public void setI(int i) {
      this.i = i;
    }
  }

  static class Destination {
    private int i;

    public Destination(int i) {
      this.i = i;
    }

    public int getI() {
      return i;
    }

    public void setI(int i) {
      this.i = i;
    }
  }

  @Test
  public void shouldConvert() {
    modelMapper.addConverter(new Converter<Source, Destination>() {
      @Override
      public Destination convert(MappingContext<Source, Destination> context) {
        return new Destination(context.getSource().getI());
      }
    });

    assert modelMapper.map(new Source(555), Destination.class).i == 555;
  }
}
