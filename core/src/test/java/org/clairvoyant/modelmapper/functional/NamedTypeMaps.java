package org.clairvoyant.modelmapper.functional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.clairvoyant.modelmapper.AbstractTest;
import org.clairvoyant.modelmapper.Converter;
import org.clairvoyant.modelmapper.TypeMap;
import org.clairvoyant.modelmapper.spi.MappingContext;
import org.testng.annotations.Test;

@Test
public class NamedTypeMaps extends AbstractTest {
  static class Source {
    String value;
  }

  static class Dest {
    String value;
  }

  public void shouldDistinguishTypeMapsByName() {
    Source source = new Source();
    source.value = "foo";

    modelMapper.createTypeMap(Source.class, Dest.class);
    modelMapper.createTypeMap(Source.class, Dest.class, "converted").setConverter(
        new Converter<Source, Dest>() {
          public Dest convert(MappingContext<Source, Dest> context) {
            Dest dest = new Dest();
            dest.value = "bar";
            return dest;
          }
        });

    assertEquals(modelMapper.map(source, Dest.class).value, source.value);
    assertEquals(modelMapper.map(source, Dest.class, "converted").value, "bar");
  }

  public void shouldGetNamedTypeMap() {
    TypeMap<Source, Dest> typeMap = modelMapper.createTypeMap(Source.class, Dest.class, "converted");
    assertNull(modelMapper.getTypeMap(Source.class, Dest.class));
    assertEquals(modelMapper.getTypeMap(Source.class, Dest.class, "converted"), typeMap);
    TypeMap<Source, Dest> typeMap2 = modelMapper.createTypeMap(Source.class, Dest.class);
    assertEquals(modelMapper.getTypeMap(Source.class, Dest.class), typeMap2);
  }
}
