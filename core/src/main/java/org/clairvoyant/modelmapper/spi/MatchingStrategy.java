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
package org.clairvoyant.modelmapper.spi;

/**
 * Identifies source to destination property matches by comparing source and destination type, property
 * and property type names.
 * 
 * @author Jonathan Halterman
 */
public interface MatchingStrategy {
  /**
   * Indicates whether the matching strategy determines exact matches that are guaranteed not to be
   * ambiguous with any other source and destination properties in the object graph. If false,
   * ModelMapper will search the object graph for duplicate matches for each destination property
   * hierarchy.
   * 
   * @return true if the matching strategy determines exact matches else false
   */
  boolean isExact();

  /**
   * Determines whether the data contained in the {@code propertyNameInfo} represents a source to
   * destination match.
   * 
   * @param propertyNameInfo to match against
   * @return true if the {@code propertyNameInfo} represents a match else false
   */
  boolean matches(PropertyNameInfo propertyNameInfo);
}
