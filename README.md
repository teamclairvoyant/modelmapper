# ModelMapper 
[![Build Status](https://travis-ci.org/modelmapper/modelmapper.svg)](https://travis-ci.org/modelmapper/modelmapper) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.clairvoyant.modelmapper/modelmapper/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.clairvoyant.modelmapper/modelmapper)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![JavaDoc](http://javadoc-badge.appspot.com/org.clairvoyant.modelmapper/modelmapper.svg?label=javadoc)](http://modelmapper.org/javadoc/)

ModelMapper is an intelligent object mapping library that automatically maps objects to each other. It uses a convention based approach while providing a simple refactoring safe API for handling specific use cases.

Visit [modelmapper.org](http://modelmapper.org) to learn more.

## Modules

In v2.0, ModelMapper provides modules for 3rd library integration you can easily register by ```modelMapper.registerModule(new TheModule())```.

- [Java 8](https://github.com/chhsiao90/modelmapper-module-java8)
- [Protocol Buffer](https://github.com/modelmapper/modelmapper/tree/master/extensions/protobuf)
- [Vavr](https://github.com/julianps/modelmapper-module-vavr)

## Related Projects

- [Spring Boot ModelMapper Starter](https://github.com/jmnarloch/modelmapper-spring-boot-starter)

## Contributing

Bug reports and feature requests are welcome via the [issue tracker](https://github.com/modelmapper/modelmapper/issues). Fixes and enhancements are also welcome via pull requests. If you're unsure about a contribution idea, feel free to [contact me][me].

[me]: mailto:jhalterman@gmail.com
