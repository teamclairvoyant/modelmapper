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
package org.clairvoyant.modelmapper.convention;

import org.clairvoyant.modelmapper.internal.util.Strings;
import org.clairvoyant.modelmapper.spi.NameTransformer;
import org.clairvoyant.modelmapper.spi.NameableType;

/**
 * {@link NameTransformer} implementations.
 * 
 * @author Jonathan Halterman
 */
public class NameTransformers {
  /**
   * Transforms accessor names to their simple property name according to the JavaBeans convention.
   * Class and field names are unchanged.
   */
  public static final NameTransformer JAVABEANS_ACCESSOR = new NameTransformer() {
    public String transform(String name, NameableType nameableType) {
      if (NameableType.METHOD.equals(nameableType)) {
        if (name.startsWith("get") && name.length() > 3)
          return Strings.decapitalize(name.substring(3));
        else if (name.startsWith("is") && name.length() > 2)
          return Strings.decapitalize(name.substring(2));
      }

      return name;
    }

    @Override
    public String toString() {
      return "Javabeans Accessor";
    }
  };

  /**
   * Transforms mutator names to their simple property name according to the JavaBeans convention.
   * Class and field names are unchanged.
   */
  public static final NameTransformer JAVABEANS_MUTATOR = new NameTransformer() {
    public String transform(String name, NameableType nameableType) {
      if (NameableType.METHOD.equals(nameableType) && name.startsWith("set") && name.length() > 3)
        return Strings.decapitalize(name.substring(3));
      return name;
    }

    @Override
    public String toString() {
      return "Javabeans Mutator";
    }
  };

  /**
   * Creates NameTransformer for builder.
   * @return a NameTransformer
   */
  public static NameTransformer builder() {
    return builder("");
  }

  /**
   * Creates NameTransformer for builder.
   *
   * @param prefix the prefix for the setter of the builder
   * @return a NameTransformer
   */
  public static NameTransformer builder(String prefix) {
    return new BuilderNameTransformer(prefix);
  }

  /**
   * Transforms the names to their simple property name according to the builder convention.
   * Class and field names are unchanged.
   */
  private static class BuilderNameTransformer implements NameTransformer {
    private String prefix;

    private BuilderNameTransformer(String prefix) {
      this.prefix = prefix;
    }

    public String transform(String name, NameableType nameableType) {
      if (prefix.isEmpty())
        return name;
      if (name.startsWith(prefix))
        return Strings.decapitalize(name.substring(prefix.length()));
      return name;
    }

    @Override
    public String toString() {
      return "Builder(prefix=" + prefix + ")";
    }
  }
}
