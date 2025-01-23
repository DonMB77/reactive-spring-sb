package com.drifter.reactive_spring_sb.repositories;

import com.drifter.reactive_spring_sb.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class PersonRepositoryImpl implements PersonRepository {

    Map<Integer, Person> personMap;
    Person marc;
    Person don;
    Person mick;
    Person tom;

    public PersonRepositoryImpl() {

        this.personMap = new HashMap<>();

        this.marc = Person.builder()
                .id(1)
                .firstName("Marc")
                .lastName("customLastName")
                .build();

        this.don = Person.builder()
                .id(2)
                .firstName("Don")
                .lastName("customLastName2")
                .build();

        this.mick = Person.builder()
                .id(3)
                .firstName("Mick")
                .lastName("customLastName3")
                .build();

        this.tom = Person.builder()
                .id(4)
                .firstName("Tom")
                .lastName("customLastName4")
                .build();

        personMap.put(marc.getId(), marc);
        personMap.put(don.getId(), don);
        personMap.put(mick.getId(), mick);
        personMap.put(tom.getId(), tom);
    }

    @Override
    public Mono<Person> getById(final Integer id) {
        return findAll().filter(person -> person.getId() == id).single();
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(marc, don, mick, tom);
    }
}
