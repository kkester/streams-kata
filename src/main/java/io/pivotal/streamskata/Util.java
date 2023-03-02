package io.pivotal.streamskata;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.groupingBy;

public class Util {

    public static List<String> mapToUppercase(List<String> strings) {
        return strings.stream()
            .map(String::toUpperCase)
            .collect(toList());
    }

    public static List<String> removeElementsWithMoreThanFourCharacters(List<String> collection) {
        return collection.stream()
            .filter(s -> s.length() < 4)
            .collect(toList());
    }

    public static List<String> sortStrings(List<String> values) {
        return values.stream()
            .sorted(Comparator.naturalOrder())
            .collect(toList());
    }

    public static List<Integer> sortIntegers(List<String> values) {
        return values.stream()
            .map(Integer::valueOf)
            .sorted()
            .collect(toList());
    }

    public static List<Integer> sortIntegersDescending(List<String> values) {
        return values.stream()
            .map(Integer::valueOf)
            .sorted(Comparator.<Integer>reverseOrder())
            .collect(toList());
    }

    public static Integer sum(List<Integer> numbers) {
        return numbers.stream()
            .mapToInt(Integer::intValue)
            .sum();
    }

    public static List<String> flattenToSingleCollection(List<List<String>> collection) {
        return collection.stream()
            .flatMap(List::stream)
            .toList();
    }

    public static String separateNamesByComma(List<Person> collection) {
        return collection.stream()
            .map(Person::getName)
            .collect(joining(", ", "Names: ", "."));
    }

    public static String findNameOfOldestPerson(List<Person> collection) {
        return collection.stream()
            .max(Comparator.comparingInt(i -> i.getAge()))
            .map(Person::getName)
            .orElse(null);
    }

    public static List<String> filterPeopleLessThan18YearsOld(List<Person> collection) {
        Function<Person,String> nameFunction = Person::getName;
        return collection.stream()
            .filter(p -> p.getAge() < 18)
            .map(nameFunction)
            .toList();
    }

    public static IntSummaryStatistics getSummaryStatisticsForAge(List<Person> collection) {
        return collection.stream()
//            .map(Person::getAge)
            .collect(summarizingInt(Person::getAge));
    }

    public static Map<Boolean, List<Person>> partitionAdults(List<Person> collection) {
        return collection.stream()
            .collect(toMap(
                p -> p.getAge() > 18,
                List::of,
                (n,c) -> Stream.concat(n.stream(),c.stream()).collect(toList())
            ));
    }

    public static Map<String, List<Person>> partitionByNationality(List<Person> collection) {
        return collection.stream()
            .collect(groupingBy(Person::getCountry));
    }

}
