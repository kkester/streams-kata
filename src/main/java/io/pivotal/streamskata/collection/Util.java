package io.pivotal.streamskata.collection;

import io.pivotal.streamskata.Person;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.groupingBy;

public class Util {

    public static List<String> mapToUppercase(List<String> input) {
        return input.stream()
            .map(String::toUpperCase)
            .toList();
    }

    public static List<String> removeElementsWithMoreThanFourCharacters(List<String> input) {
        return input.stream()
            .filter(s -> s.length() < 4)
            .toList();
    }

    public static List<String> sortStrings(List<String> input) {
        return input.stream()
            .sorted(Comparator.naturalOrder())
            .toList();
    }

    public static List<Integer> sortIntegers(List<String> input) {
        return input.stream()
            .map(Integer::valueOf)
            .sorted()
            .toList();
    }

    public static List<Integer> sortIntegersDescending(List<String> input) {
        return input.stream()
            .map(Integer::valueOf)
            .sorted(Comparator.<Integer>reverseOrder())
            .toList();
    }

    public static Integer sum(List<Integer> numbers) {
        return numbers.stream()
            .mapToInt(Integer::intValue)
            .sum();
    }

    public static List<String> flattenToSingleCollection(List<List<String>> input) {
        return input.stream()
            .flatMap(List::stream)
            .toList();
    }

    public static String separateNamesByComma(List<Person> input) {
        return input.stream()
            .map(Person::getName)
            .collect(joining(", ", "Names: ", "."));
    }

    public static String findNameOfOldestPerson(List<Person> input) {
        return input.stream()
            .max(Comparator.comparingInt(Person::getAge))
            .map(Person::getName)
            .orElse(null);
    }

    public static List<String> filterPeopleLessThan18YearsOld(List<Person> input) {
        Function<Person,String> nameFunction = Person::getName;
        return input.stream()
            .filter(p -> p.getAge() < 18)
            .map(nameFunction)
            .toList();
    }

    public static IntSummaryStatistics getSummaryStatisticsForAge(List<Person> input) {
        return input.stream()
            .collect(summarizingInt(Person::getAge));
    }

    public static Map<Boolean, List<Person>> partitionAdults(List<Person> input) {
        return input.stream()
            .collect(toMap(
                p -> p.getAge() > 18,
                List::of,
                (n,c) -> Stream.concat(n.stream(),c.stream()).toList()
            ));
    }

    public static Map<String, List<Person>> partitionByNationality(List<Person> input) {
        return input.stream()
            .collect(groupingBy(Person::getCountry));
    }

}
