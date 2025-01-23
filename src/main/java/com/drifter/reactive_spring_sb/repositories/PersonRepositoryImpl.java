package com.drifter.reactive_spring_sb.repositories;

import com.drifter.reactive_spring_sb.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {

    Person marc = Person.builder()
            .id(1)
            .firstName("Marc")
            .lastName("customLastName")
            .build();

    Person don = Person.builder()
            .id(2)
            .firstName("Don")
            .lastName("customLastName2")
            .build();

    Person mick = Person.builder()
            .id(3)
            .firstName("Mick")
            .lastName("customLastName3")
            .build();

    Person tom = Person.builder()
            .id(4)
            .firstName("Tom")
            .lastName("customLastName4")
            .build();

    @Override
    public Mono<Person> getById(Integer id) {
        return Mono.just(mick);
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(marc, don, mick, tom);
    }
}
