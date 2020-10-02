package org.clairvoyant.modelmapper.functional.lambda;

import org.clairvoyant.modelmapper.AbstractTest;
import org.clairvoyant.modelmapper.Asserts;
import org.clairvoyant.modelmapper.Condition;
import org.clairvoyant.modelmapper.ConfigurationException;
import org.clairvoyant.modelmapper.Converter;
import org.clairvoyant.modelmapper.ExpressionMap;
import org.clairvoyant.modelmapper.ModelMapper;
import org.clairvoyant.modelmapper.TypeMap;
import org.clairvoyant.modelmapper.builder.ConfigurableConditionExpression;
import org.clairvoyant.modelmapper.spi.DestinationSetter;
import org.clairvoyant.modelmapper.spi.MappingContext;
import org.clairvoyant.modelmapper.spi.SourceGetter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

@Test
@SuppressWarnings("unused")
public class TypeMapLambdaTest extends AbstractTest {
  static class Src {
    String srcText;

    Src(String srcText) {
      this.srcText = srcText;
    }

    String getSrcText() {
      return srcText;
    }

    public void setSrcText(String srcText) {
      this.srcText = srcText;
    }
  }

  static class Dest {
    String destText;

    public String getDestText() {
      return destText;
    }

    void setDestText(String destText) {
      this.destText = destText;
    }
  }

  @BeforeMethod
  public void setUp() {
    modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setImplicitMappingEnabled(false);
  }

  public void shouldAddMapping() {
    TypeMap<Src, Dest> typeMap = modelMapper.createTypeMap(Src.class, Dest.class);
    typeMap.addMapping(srcGetter(), destSetter());

    typeMap.validate();
    assertEquals(typeMap.map(new Src("foo")).destText, "foo");
  }

  public void shouldAddMappingWithConverter() {
    TypeMap<Src, Dest> typeMap = modelMapper.createTypeMap(Src.class, Dest.class);
    typeMap.addMappings(
        new ExpressionMap<Src, Dest>() {
          public void configure(ConfigurableConditionExpression<Src, Dest> mapping) {
            mapping.using(new Converter<String, String>() {
              public String convert(MappingContext<String, String> context) {
                return context.getSource().toUpperCase();
              }
            }).map(srcGetter(), destSetter());
          }
        });

    typeMap.validate();
    assertEquals(typeMap.map(new Src("foo")).destText, "FOO");
  }

  public void shouldAddMappingWithSkip() {
    TypeMap<Src, Dest> typeMap = modelMapper.createTypeMap(Src.class, Dest.class);
    typeMap.addMappings(
        new ExpressionMap<Src, Dest>() {
          public void configure(ConfigurableConditionExpression<Src, Dest> mapping) {
            mapping.skip(destSetter());
          }
        });

    typeMap.validate();
    assertNull(typeMap.map(new Src("foo")).destText);
  }

  public void shouldAddMappingWithCondition() {
    TypeMap<Src, Dest> typeMap = modelMapper.createTypeMap(Src.class, Dest.class);
    typeMap.addMappings(
        new ExpressionMap<Src, Dest>() {
          public void configure(ConfigurableConditionExpression<Src, Dest> mapping) {
            mapping.when(new Condition<String, String>() {
              public boolean applies(MappingContext<String, String> context) {
                return context.getSource().equals("foo");
              }
            }).map(srcGetter(), destSetter());
          }
        });

    typeMap.validate();
    assertNull(typeMap.map(new Src("bar")).destText);
    assertEquals(typeMap.map(new Src("foo")).destText, "foo");
  }

  public void shouldFailedWithEmptyDestinationSetter() {
    TypeMap<Src, Dest> typeMap = modelMapper.createTypeMap(Src.class, Dest.class);

    try {
      typeMap.addMapping(srcGetter(), new DestinationSetter<Dest, String>() {
        public void accept(Dest destination, String value) {
        }
      });
      fail();
    } catch (ConfigurationException e) {
      Asserts.assertContains(e.getMessage(), "Illegal DestinationSetter defined");
    }
  }

  private static SourceGetter<Src> srcGetter() {
    return new SourceGetter<Src>() {
      public Object get(Src source) {
        return source.getSrcText();
      }
    };
  }

  private static DestinationSetter<Dest, String> destSetter() {
    return new DestinationSetter<Dest, String>() {
      public void accept(Dest destination, String value) {
        destination.setDestText(value);
      }
    };
  }
}
