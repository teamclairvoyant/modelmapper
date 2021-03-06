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
package org.clairvoyant.modelmapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.clairvoyant.modelmapper.internal.Errors;
import org.clairvoyant.modelmapper.spi.ErrorMessage;

/**
 * Thrown when invalid configuration is detected.
 * 
 * @author Jonathan Halterman
 */
public class ConfigurationException extends RuntimeException {
  private static final long serialVersionUID = 0;
  private final List<ErrorMessage> messages;

  public ConfigurationException(List<ErrorMessage> messages) {
    this.messages = new ArrayList<ErrorMessage>(messages);
    initCause(Errors.getOnlyCause(this.messages));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage() {
    return Errors.format("ModelMapper configuration errors", messages);
  }

  /** Returns messages for the errors that caused this exception. */
  public Collection<ErrorMessage> getErrorMessages() {
    return messages;
  }
}
