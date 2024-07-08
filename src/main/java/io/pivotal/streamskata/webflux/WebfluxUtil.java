package io.pivotal.streamskata.webflux;

import io.pivotal.streamskata.Person;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Slf4j
public class WebfluxUtil {

    static final List<String> tokens = new ArrayList<>();

    public static Flux<String> mapToUppercase(String... inputs) {
        return Flux.just(inputs)
            .map(String::toUpperCase);
    }

    public static Flux<String> removeElementsWithMoreThanFourCharacters(String... inputs) {
        return Flux.just(inputs)
            .filter(s -> s.length() < 4);
    }

    public static Flux<String> sortStrings(String... inputs) {
        return Flux.just(inputs)
            .sort();
    }

    public static Flux<Integer> sortIntegers(String... inputs) {
        return Flux.just(inputs)
            .map(Integer::valueOf)
            .sort();
    }

    public static Flux<Integer> sortIntegersDescending(String... inputs) {
        return Flux.just(inputs)
            .map(Integer::valueOf)
            .sort(Comparator.reverseOrder());
    }

    public static Mono<Integer> sum(Integer... inputs) {
        return Flux.just(inputs)
            .reduce(0, Integer::sum);
    }

    public static Mono<String> separateNamesByComma(List<Person> people) {
        return Flux.fromIterable(people)
            .map(Person::getName)
            .reduce((a, b) -> a + ", " + b)
            .map(r -> "Names: " + r + ".");
    }

    public static Mono<String> findNameOfOldestPerson(List<Person> people) {
        return Flux.fromIterable(people)
            .reduce((a, b) -> a.getAge() > b.getAge() ? a : b)
            .map(Person::getName);
    }

    public static Flux<String> filterPeopleLessThan18YearsOld(List<Person> input) {
        return Flux.fromIterable(input)
            .filter(person -> person.getAge() < 18)
            .map(Person::getName);
    }

    public static Mono<String> generateAndSaveToken() {
        log.info("Generating Token");
        return createToken().delayUntil(WebfluxUtil::saveToken);
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

    public static void addYoungestAndOldestToResults(List<Person> people, Map<String, String> results) {
        Mono<String> oldestFlux = Flux.fromIterable(people)
            .reduce((a, b) -> a.getAge() > b.getAge() ? a : b)
            .map(Person::getName)
            .doOnNext(name -> results.put("oldest", name));
        Mono<String> youngestFlux = Flux.fromIterable(people)
            .reduce((a, b) -> a.getAge() < b.getAge() ? a : b)
            .map(Person::getName)
            .doOnNext(name -> results.put("youngest", name));
        Flux.concat(oldestFlux, youngestFlux)
            .subscribe();
    }
}
