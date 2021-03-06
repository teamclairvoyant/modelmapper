package org.clairvoyant.modelmapper.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.clairvoyant.modelmapper.internal.Errors;
import org.clairvoyant.modelmapper.spi.ConditionalConverter;
import org.clairvoyant.modelmapper.spi.MappingContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Converts {@link ArrayNode} instances to {@link Collection} instances.
 *
 * @author Chun Han Hsiao
 */
public class CollectionToArrayNodeConverter implements ConditionalConverter<Collection<Object>, ArrayNode> {
  private ObjectMapper objectMapper;

  public CollectionToArrayNodeConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
    return Collection.class.isAssignableFrom(sourceType) && ArrayNode.class.isAssignableFrom(destinationType)
        ? MatchResult.FULL : MatchResult.NONE;
  }

  @Override
  public ArrayNode convert(MappingContext<Collection<Object>, ArrayNode> context) {
    Collection<Object> source = context.getSource();
    if (source == null)
      return null;

    ArrayNode destination = context.getDestination() == null ? objectMapper.createArrayNode()
        : context.getDestination();

    for (Object sourceElement : source) {
      if (sourceElement != null) {
        destination.add(objectMapper.valueToTree(sourceElement));
      } else {
        destination.add(MissingNode.getInstance());
      }
    }

    return destination;
  }

  private void addPrimitiveElement(ArrayNode destination, Object element) {
    Class<?> elementType = element.getClass();
    try {
      Method method = destination.getClass().getMethod("add", elementType);
      method.invoke(destination, element);
    } catch (NoSuchMethodException e) {
      throw new Errors().addMessage("Unsupported type: %s", elementType.getName()).toMappingException();
    } catch (IllegalAccessException e) {
      throw new Errors().addMessage("Unable to map Array->Collection", elementType.getName()).toMappingException();
    } catch (InvocationTargetException e) {
      throw new Errors().addMessage("Unable to map Array->Collection", elementType.getName()).toMappingException();
    }
  }
}
