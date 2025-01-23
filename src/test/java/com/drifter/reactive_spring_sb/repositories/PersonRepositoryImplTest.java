package com.drifter.reactive_spring_sb.repositories;

import com.drifter.reactive_spring_sb.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonRepositoryImplTest {

    PersonRepository personRepository = new PersonRepositoryImpl();

    @Test
    void testGetByIdFound() {
        Mono<Person> foundPersonMono = personRepository.getById(3);

        assertTrue(foundPersonMono.hasElement().block());
    }

    @Test
    void testGetByIdNotFound() {
        Mono<Person> foundPersonMono = personRepository.getById(999);

        //assertThat(foundPersonMono.hasElement().block()).isFalse();
    }

    // test for blocking
    @Test
    void testMonoByIdBlock() {
        Mono<Person> personMono = personRepository.getById(1);

        Person person = personMono.block();

        System.out.println(person.toString());
    }

    @Test
    void testGetByIdSubscriber() {
        Mono<Person> personMono = personRepository.getById(1);

        personMono.subscribe(person -> System.out.println(person.toString()));
    }

    @Test
    void testMapOperation() {
        Mono<Person> personMono = personRepository.getById(1);

        personMono.map(Person::getFirstName).subscribe(System.out::println);
    }

    @Test
    void testFluxBlockFirst() {
        Flux<Person> personFlux = personRepository.findAll();

        Person person = personFlux.blockFirst();

        System.out.println(person.toString());
    }

    @Test
    void testFluxSubscriber() {
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testFluxMap() {
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.map(Person::getFirstName).subscribe(System.out::println);
    }

    @Test
    void testFluxToList() {
        Flux<Person> personFlux = personRepository.findAll();

        Mono<List<Person>> listMono = personFlux.collectList();

        listMono.subscribe(list -> {
            list.forEach(person -> System.out.println(person.getFirstName()));
        });
    }

    @Test
    void testFilterOnName() {
        personRepository.findAll()
                .filter(person -> person.getFirstName().equals("Marc"))
                .subscribe(person -> System.out.println(person.getLastName()));
    }

    @Test
    void testGetById() {
        Mono<Person> marcMono = personRepository.findAll().filter(person -> person.getFirstName().equals("Marc"))
                .next();

        marcMono.subscribe(person -> System.out.println(person.getFirstName()));
    }

    @Test
    void testFindPersonByIdNotFound() {
        Flux<Person> personFlux = personRepository.findAll();

        final Integer id = 888;

        Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).single()
                .doOnError(throwable -> {
                    System.out.println("Error occurred in flux");
                    System.out.println(throwable.toString());
                });

        // without the subscriber there is no backpressure to create the mono.
        personMono.subscribe(person -> {
            System.out.println(person.toString());
        }, throwable -> {
            System.out.println("Error occurred in the mono");
            System.out.println(throwable.toString());
        });
    }
}