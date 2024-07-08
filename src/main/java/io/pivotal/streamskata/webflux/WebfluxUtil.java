package io.pivotal.streamskata.webflux;

import io.pivotal.streamskata.Person;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class WebfluxUtil {

    static final List<String> tokens = new ArrayList<>();

    public static Flux<String> mapToUppercase(String...inputs) {
        return Flux.fromArray(inputs);
    }

    public static Flux<String> removeElementsWithMoreThanFourCharacters(String...inputs) {
        return Flux.fromArray(inputs);
    }
    public static Flux<String> sortStrings(String...inputs) {
        return Flux.fromArray(inputs);
    }

    public static Flux<Integer> sortIntegers(String...inputs) {
        return Flux.empty();
    }

    public static Flux<Integer> sortIntegersDescending(String...inputs) {
        return Flux.empty();
    }

    public static Mono<Integer> sum(Integer...inputs) {
        return Mono.empty();
    }

    public static Mono<String> separateNamesByComma(List<Person> people) {
        return Flux.fromIterable(people)
            .map(Person::getName)
            .last();
    }

    public static Mono<String> findNameOfOldestPerson(List<Person> people) {
        return Flux.fromIterable(people)
            .map(Person::getName)
            .last();
    }

    public static void addYoungestAndOldestToResults(List<Person> people, List<String> results) {

    }

    public static Flux<String> filterPeopleLessThan18YearsOld(List<Person> people) {
        return Flux.fromIterable(people)
            .map(Person::getName);
    }

    public static Mono<String> generateAndSaveToken() {
        log.info("Generating Token");
        return Mono.empty();
    }

    private static Mono<String> createToken() {
        return Mono.fromCallable(() -> {
            log.info("Creating Token");
            String token = UUID.randomUUID().toString();
            log.info("Done Creating Token");
            return token;
        });
    }

    private static Mono<Void> saveToken(String token) {
        return Mono.fromRunnable(() -> {
            log.info("Saving Token");
            tokens.add(token);
            log.info("Done Saving Token");
        });
    }
}
