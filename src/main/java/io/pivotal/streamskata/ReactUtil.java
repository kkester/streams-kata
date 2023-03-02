package io.pivotal.streamskata;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

public class ReactUtil {
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
}
