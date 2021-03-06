package org.clairvoyant.modelmapper.internal.converter;

import static org.testng.Assert.fail;

import org.clairvoyant.modelmapper.Fixtures;
import org.clairvoyant.modelmapper.ModelMapper;
import org.clairvoyant.modelmapper.internal.InheritingConfiguration;
import org.clairvoyant.modelmapper.internal.MappingContextImpl;
import org.clairvoyant.modelmapper.internal.MappingEngineImpl;
import org.clairvoyant.modelmapper.spi.ConditionalConverter;
import org.clairvoyant.modelmapper.spi.MappingEngine;
import org.testng.annotations.BeforeMethod;

/**
 * @author Jonathan Halterman
 */
public abstract class AbstractConverterTest {
  protected final ConditionalConverter<Object, Object> converter;
  private Class<Object> destinationType;
  protected ModelMapper modelMapper;
  protected InheritingConfiguration config = new InheritingConfiguration();
  private MappingEngine engine = new MappingEngineImpl(config);

  @SuppressWarnings("unchecked")
  AbstractConverterTest(ConditionalConverter<?, ?> converter) {
    this(converter, Object.class);
  }

  @BeforeMethod
  protected void init() {
    modelMapper = Fixtures.createModelMapper();
  }

  @SuppressWarnings("unchecked")
  AbstractConverterTest(ConditionalConverter<?, ?> converter, Class<?> destinationType) {
    this.converter = (ConditionalConverter<Object, Object>) converter;
    this.destinationType = (Class<Object>) destinationType;
  }

  /**
   * Converts {@code source} from {@code source.getClass()} to {@link #destinationType} using
   * {@link #converter}. Assumes that {@link #destinationType} has been set in the constructor.
   */
  @SuppressWarnings("unchecked")
  protected Object convert(Object source) {
    return converter.convert(new MappingContextImpl<Object, Object>(source,
        (Class<Object>) source.getClass(), null, destinationType, null, null, (MappingEngineImpl) engine));
  }

  /**
   * Converts {@code source} from {@code source.getClass()} to {@code destinationType} using
   * {@link #converter}.
   */
  @SuppressWarnings("unchecked")
  protected Object convert(Object source, Class<?> destinationType) {
    return converter.convert(new MappingContextImpl<Object, Object>(source,
        (Class<Object>) source.getClass(), null, (Class<Object>) destinationType, null, null,
        (MappingEngineImpl) engine));
  }

  /**
   * Converts {@code source} from {@code source.getClass()} to {@code destination} using
   * {@link #converter}.
   */
  @SuppressWarnings("unchecked")
  protected Object convert(Object source, Object destination) {
    return converter.convert(new MappingContextImpl<Object, Object>(source,
        (Class<Object>) source.getClass(), destination, destinationType,
        null, null, (MappingEngineImpl) engine));
  }

  /**
   * Converts {@code source} from {@code source.getClass()} to {@code destination} using
   * {@link #converter}.
   */
  @SuppressWarnings("unchecked")
  protected Object convert(Object source, Object destination, Class<?> destinationType) {
    return converter.convert(new MappingContextImpl<Object, Object>(source,
        (Class<Object>) source.getClass(), destination, (Class<Object>) destinationType,
        null, null, (MappingEngineImpl) engine));
  }

  protected void assertInvalid(Object source, Class<?> destinationType) {
    try {
      convert(source, destinationType);
      fail();
    } catch (Exception expected) {
      // DO Nothing
    }
  }
}
