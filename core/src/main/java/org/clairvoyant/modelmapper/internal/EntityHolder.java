package org.clairvoyant.modelmapper.internal;

import java.util.List;

/**
 * This interface will be implemented by the proxy created using ByteBuddy
 * for the GraphQL DTO.
 * @author satyanandana
 * @param <T> type of the Entity
 *
 */
public interface EntityHolder<T> {

    void setEntity(T t);

    T getEntity();

    void  setMappingContext(MappingContextImpl mappingContext);

    MappingContextImpl getMappingContext();

    void setIntercepted(List<String> getterNames);

    List<String> getIntercepted();

}
