package io.pivotal.streamskata.webflux;

import io.pivotal.streamskata.Person;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Slf4j
public class WebfluxUtil {

    static final List<String> tokens = new ArrayList<>();

    public static Flux<String> mapToUppercase(String...inputs) {
        throw new RuntimeException();
    }

    public static Flux<String> removeElementsWithMoreThanFourCharacters(String...inputs) {
        throw new RuntimeException();
    }
    public static Flux<String> sortStrings(String...inputs) {
        throw new RuntimeException();
    }

    public static Flux<Integer> sortIntegers(String...inputs) {
        throw new RuntimeException();
    }

    public static Flux<Integer> sortIntegersDescending(String...inputs) {
        throw new RuntimeException();
    }

    public static Mono<Integer> sum(Integer...inputs) {
        throw new RuntimeException();
    }

    public static Mono<String> separateNamesByComma(List<Person> people) {
        throw new RuntimeException();
    }

    public static Mono<String> findNameOfOldestPerson(List<Person> people) {
        throw new RuntimeException();
    }

    public static Mono<String> generateAndSaveToken() {
        log.info("Generating Token");
        throw new RuntimeException();
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

    }
}
