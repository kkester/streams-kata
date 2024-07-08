package io.pivotal.streamskata.collection;

import io.pivotal.streamskata.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class UtilTest {

    @Test
    void shouldMapStringsToUpperCase() {
        List<String> input = asList("This", "is", "java", "8");
        List<String> result = Util.mapToUppercase(input);
        assertThat(result).contains("THIS", "IS", "JAVA", "8");
    }

    @Test
    void shouldRemoveElementsWithMoreThanThreeCharacters() {
        List<String> input = asList("This", "is", "java", "8");
        List<String> result = Util.removeElementsWithMoreThanFourCharacters(input);
        assertThat(result).containsExactly("is", "8");
    }

    @Test
    void shouldSortStrings() {
        List<String> input = Arrays.asList("C", "F", "A", "D", "B", "E");
        List<String> result = Util.sortStrings(input);
        assertThat(result).containsExactly("A", "B", "C", "D", "E", "F");
    }

    @Test
    void shouldSortIntegers() {
        List<String> input = Arrays.asList("2", "4", "12", "3");
        List<Integer> result = Util.sortIntegers(input);
        assertThat(result).containsExactly(2, 3, 4, 12);
    }

    @Test
    void shouldSortIntegersInDescendingOrder() {
        List<String> input = Arrays.asList("2", "4", "12", "3");
        List<Integer> result = Util.sortIntegersDescending(input);
        assertThat(result).containsExactly(12, 4, 3, 2);
    }

    @Test
    void shouldSumIntegersInCollection() {
        List<Integer> input = asList(1, 2, 3, 4, 5);
        Integer result = Util.sum(input);
        assertThat(result).isEqualTo(1 + 2 + 3 + 4 + 5);
    }

    @Test
    void shouldFlattenCollectionToSingleCollection() {
        List<List<String>> input = asList(
                asList("Duke", "Fred"),
                asList("John", "George", "Paal"));

        List<String> result = Util.flattenToSingleCollection(input);
        assertThat(result).containsExactlyInAnyOrder("Duke", "Fred", "John", "George", "Paal");
    }

    @Test
    void shouldSeparateNamesByComma() {
        List<Person> input = asList(
                new Person("Duke"),
                new Person("Fred"),
                new Person("John"));

        String result = Util.separateNamesByComma(input);
        assertThat(result).isEqualTo("Names: Duke, Fred, John.");
    }

    @Test
    void shouldFindNameOfOldestPerson() {
        List<Person> input = asList(
                new Person("Duke", 10),
                new Person("Fred", 28),
                new Person("John", 45));

        String result = Util.findNameOfOldestPerson(input);
        assertThat(result).isEqualTo("John");
    }

    @Test
    void shouldFilterPeopleLessThan18YearsOld() {
        List<Person> input = asList(
                new Person("Duke", 10),
                new Person("Fred", 28),
                new Person("John", 45),
                new Person("Marius", 17));

        List<String> result = Util.filterPeopleLessThan18YearsOld(input);
        assertThat(result).containsExactlyInAnyOrder("Duke", "Marius");
    }

    @Test
    void shouldRetrieveSummaryStatisticsForAge() {
        List<Person> input = asList(
                new Person("Duke", 10),
                new Person("Fred", 28),
                new Person("John", 45));

        IntSummaryStatistics summaryStatistics = Util.getSummaryStatisticsForAge(input);

        assertThat(summaryStatistics).isNotNull();
        assertThat(summaryStatistics.getAverage()).isEqualTo((double) (10 + 28 + 45) / 3);
        assertThat(summaryStatistics.getSum()).isEqualTo((long) 10 + 28 + 45);
        assertThat(summaryStatistics.getMin()).isEqualTo(10);
        assertThat(summaryStatistics.getMax()).isEqualTo(45);
    }

    @Test
    void shouldPartitionAdults() {
        Person duke = new Person("Duke", 10);
        Person fred = new Person("Fred", 28);
        Person john = new Person("John", 45);
        List<Person> input = asList(duke, fred, john);

        Map<Boolean, List<Person>> result = Util.partitionAdults(input);

        assertThat(result.get(true)).containsExactlyInAnyOrder(fred, john);
        assertThat(result.get(false)).containsExactlyInAnyOrder(duke);
    }

    @Test
    void shouldPartitionByNationality() {
        Person duke = new Person("Duke", 10, "USA");
        Person fred = new Person("Fred", 28, "Norway");
        Person john = new Person("John", 45, "Norway");
        List<Person> input = asList(duke, fred, john);

        Map<String, List<Person>> result = Util.partitionByNationality(input);

        assertThat(result.get("USA")).containsExactly(duke);
        assertThat(result.get("Norway")).containsExactlyInAnyOrder(fred, john);
    }
}