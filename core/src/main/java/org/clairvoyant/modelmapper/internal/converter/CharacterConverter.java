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

import org.clairvoyant.modelmapper.spi.ConditionalConverter;
import org.clairvoyant.modelmapper.spi.MappingContext;

/**
 * Converts to {@link Character} instances.
 * 
 * @author Jonathan Halterman
 */
class CharacterConverter implements ConditionalConverter<Object, Character> {
  public Character convert(MappingContext<Object, Character> context) {
    Object source = context.getSource();
    if (source == null)
      return null;

    String stringValue = source.toString();
    if (stringValue.length() == 0)
      return null;
    return Character.valueOf(stringValue.charAt(0));
  }

  public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
    boolean destMatch = destinationType == Character.class || destinationType == Character.TYPE;
    return destMatch ? sourceType == Character.class || sourceType == Character.TYPE ? MatchResult.FULL
        : MatchResult.PARTIAL
        : MatchResult.NONE;
  }
}