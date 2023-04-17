package org.clairvoyant.modelmapper.bugs;

import static org.testng.Assert.assertNotNull;

import org.clairvoyant.modelmapper.Condition;
import org.clairvoyant.modelmapper.ModelMapper;
import org.clairvoyant.modelmapper.PropertyMap;
import org.clairvoyant.modelmapper.spi.MappingContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class GH267 {
  private ModelMapper modelMapper;

  @BeforeMethod
  public void setUp() {
    modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setPropertyCondition(new Condition<Object, Object>() {
      public boolean applies(MappingContext<Object, Object> context) {
        return context.getSource() != null;
      }
    });
  }

  static class A {}

  static class B {
    void setSomething(String something) {
      throw new NullPointerException(something);
    }
  }

  @Test
  public void shouldPass() {
    modelMapper.addMappings(new PropertyMap<A, B>() {
      protected void configure() {
        skip().setSomething(null);
      }
    });

    B b = modelMapper.map(new A(), B.class);
    assertNotNull(b);
  }

}
