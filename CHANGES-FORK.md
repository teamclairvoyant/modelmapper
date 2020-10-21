## Clairvoyant ModelMapper

* Moved the java packages from org.modelmapper to org.clairvoyant.modelmapper
* Added a new method to validate the property mapping org.clairvoyant.modelmapper.ModelMapper#validatePropertyMappings()
* Added a new interface - org.clairvoyant.modelmapper.internal.EntityHolder
* Made the following classes and interfaces public.
  * org.clairvoyant.modelmapper.internal.MappingContextImpl
  * org.clairvoyant.modelmapper.internal.MappingImpl
  * org.clairvoyant.modelmapper.internal.Mutator
  * org.clairvoyant.modelmapper.internal.PropertyInfoImpl
  * org.clairvoyant.modelmapper.internal.MethodAccessor
  * org.clairvoyant.modelmapper.internal.PropertyMappingImpl
  
* Modified the method org.clairvoyant.modelmapper.internal.MappingEngineImpl#propertyMap
* Modified the method org.clairvoyant.modelmapper.internal.MappingEngineImpl#resolveSourceValue
