package io.pivotal.streamskata;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

public class ReactUtil {
    public static Flux<String> mapToUppercase(String...inputs) {
        return Flux.just(inputs)
            .map(String::toUpperCase);
    }

    public static Flux<String> removeElementsWithMoreThanFourCharacters(String...inputs) {
        return Flux.just(inputs)
            .filter(s -> s.length() < 4);
    }
    public static Flux<String> sortStrings(String...inputs) {
        return Flux.just(inputs)
            .sort();
    }

    public static Flux<Integer> sortIntegers(String...inputs) {
        return Flux.just(inputs)
            .map(Integer::valueOf)
            .sort();
    }

    public static Flux<Integer> sortIntegersDescending(String...inputs) {
        return Flux.just(inputs)
            .map(Integer::valueOf)
            .sort(Comparator.reverseOrder());
    }

    public static Mono<Integer> sum(Integer...inputs) {
        return Flux.just(inputs)
            .reduce(0, Integer::sum);
    }

    public static Mono<String> separateNamesByComma(List<Person> people) {
        return Flux.fromIterable(people)
            .map(Person::getName)
            .reduce((a,b) -> a + ", " + b)
            .map(r -> "Names: " + r + ".");
    }

    public static Mono<String> findNameOfOldestPerson(List<Person> people) {
        return Flux.fromIterable(people)
            .reduce((a,b) -> a.getAge() > b.getAge() ? a : b)
            .map(Person::getName);
    }
}
