package com.drifter.reactive_spring_sb.repositories;

import com.drifter.reactive_spring_sb.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {

    Mono<Person> getById(Integer id);

    Flux<Person> findAll();
}
