/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.clairvoyant.modelmapper.internal.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.jodah.typetools.TypeResolver;
import net.jodah.typetools.TypeResolver.Unknown;

import org.clairvoyant.modelmapper.internal.util.Types;
import org.clairvoyant.modelmapper.spi.ConditionalConverter;
import org.clairvoyant.modelmapper.spi.Mapping;
import org.clairvoyant.modelmapper.spi.MappingContext;
import org.clairvoyant.modelmapper.spi.PropertyInfo;
import org.clairvoyant.modelmapper.spi.PropertyMapping;

/**
 * Converts {@link Map} instances to each other.
 *
 * @author Jonathan Halterman
 */
class MapConverter implements ConditionalConverter<Map<?, ?>, Map<Object, Object>> {
  public Map<Object, Object> convert(MappingContext<Map<?, ?>, Map<Object, Object>> context) {
    Map<?, ?> source = context.getSource();
    if (source == null)
      return null;

    Map<Object, Object> destination = context.getDestination() == null ? createDestination(context)
        : context.getDestination();
    Mapping mapping = context.getMapping();

    Class<?> keyElementType = Object.class;
    Class<?> valueElementType = Object.class;
    if (mapping != null && mapping instanceof PropertyMapping) {
      PropertyInfo destInfo = ((PropertyMapping) mapping).getLastDestinationProperty();
      Class<?>[] elementTypes = TypeResolver.resolveRawArguments(destInfo.getGenericType(),
          destInfo.getMember().getDeclaringClass());
      if (elementTypes != null) {
        keyElementType = elementTypes[0] == Unknown.class ? Object.class : elementTypes[0];
        valueElementType = elementTypes[1] == Unknown.class ? Object.class : elementTypes[1];
      }
    } else if (context.getGenericDestinationType() instanceof ParameterizedType) {
      Type[] elementTypes = ((ParameterizedType) context.getGenericDestinationType()).getActualTypeArguments();
      keyElementType = Types.rawTypeFor(elementTypes[0]);
      valueElementType = Types.rawTypeFor(elementTypes[1]);
    }

    for (Entry<?, ?> entry : source.entrySet()) {
      Object key = null;
      if (entry.getKey() != null) {
        MappingContext<?, ?> keyContext = context.create(entry.getKey(), keyElementType);
        key = context.getMappingEngine().map(keyContext);
      }

      Object value = null;
      if (entry.getValue() != null) {
        MappingContext<?, ?> valueContext = context.create(entry.getValue(), valueElementType);
        value = context.getMappingEngine().map(valueContext);
      }

      destination.put(key, value);
    }

    return destination;
  }

  public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
    return Map.class.isAssignableFrom(sourceType) && Map.class.isAssignableFrom(destinationType) ? MatchResult.FULL
        : MatchResult.NONE;
  }

  protected Map<Object, Object> createDestination(
      MappingContext<Map<?, ?>, Map<Object, Object>> context) {
    if (!context.getDestinationType().isInterface())
      return context.getMappingEngine().createDestination(context);
    if (SortedMap.class.isAssignableFrom(context.getDestinationType()))
      return new TreeMap<Object, Object>();
    return new HashMap<Object, Object>();
  }
}
