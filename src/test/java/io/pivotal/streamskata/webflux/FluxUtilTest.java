package io.pivotal.streamskata.webflux;

import io.pivotal.streamskata.Person;
import io.pivotal.streamskata.collection.Util;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class FluxUtilTest {

    @Test
    void shouldMapStringsToUpperCase() {
        Flux<String> result = ReactUtil.mapToUppercase("This", "is", "java", "8");
        StepVerifier.create(result)
            .consumeNextWith(v -> assertThat(v).isEqualTo("THIS"))
            .consumeNextWith(v -> assertThat(v).isEqualTo("IS"))
            .consumeNextWith(v -> assertThat(v).isEqualTo("JAVA"))
            .consumeNextWith(v -> assertThat(v).isEqualTo("8"))
            .expectComplete()
            .verify();
    }

    @Test
    void shouldRemoveElementsWithMoreThanThreeCharacters() {
        Flux<String> result = ReactUtil.removeElementsWithMoreThanFourCharacters("This", "is", "java", "8");
        StepVerifier.create(result)
            .consumeNextWith(v -> assertThat(v).isEqualTo("is"))
            .consumeNextWith(v -> assertThat(v).isEqualTo("8"))
            .expectComplete()
            .verify();
    }

    @Test
    void shouldSortStrings() {
        Flux<String> result = ReactUtil.sortStrings("C", "F", "A", "D", "B", "E");
        StepVerifier.create(result)
            .consumeNextWith(v -> assertThat(v).isEqualTo("A"))
            .consumeNextWith(v -> assertThat(v).isEqualTo("B"))
            .consumeNextWith(v -> assertThat(v).isEqualTo("C"))
            .consumeNextWith(v -> assertThat(v).isEqualTo("D"))
            .consumeNextWith(v -> assertThat(v).isEqualTo("E"))
            .consumeNextWith(v -> assertThat(v).isEqualTo("F"))
            .expectComplete()
            .verify();
    }

    @Test
    void shouldSortIntegers() {
        Flux<Integer> result = ReactUtil.sortIntegers("2", "4", "12", "3");
        StepVerifier.create(result)
            .consumeNextWith(v -> assertThat(v).isEqualTo(2))
            .consumeNextWith(v -> assertThat(v).isEqualTo(3))
            .consumeNextWith(v -> assertThat(v).isEqualTo(4))
            .consumeNextWith(v -> assertThat(v).isEqualTo(12))
            .expectComplete()
            .verify();
    }

    @Test
    void shouldSortIntegersInDescendingOrder() {
        Flux<Integer> result = ReactUtil.sortIntegersDescending("2", "4", "12", "3");
        StepVerifier.create(result)
            .consumeNextWith(v -> assertThat(v).isEqualTo(12))
            .consumeNextWith(v -> assertThat(v).isEqualTo(4))
            .consumeNextWith(v -> assertThat(v).isEqualTo(3))
            .consumeNextWith(v -> assertThat(v).isEqualTo(2))
            .expectComplete()
            .verify();
    }

    @Test
    void shouldSumIntegersInCollection() {
        Mono<Integer> result = ReactUtil.sum(1, 2, 3, 4, 5);
        StepVerifier.create(result)
            .consumeNextWith(v -> assertThat(v).isEqualTo(1 + 2 + 3 + 4 + 5))
            .expectComplete()
            .verify();
    }

    @Test
    void shouldSeparateNamesByComma() {
        List<Person> input = asList(
            new Person("Duke"),
            new Person("Fred"),
            new Person("John"));

        Mono<String> result = ReactUtil.separateNamesByComma(input);
        StepVerifier.create(result)
            .assertNext(r -> assertThat(r).isEqualTo("Names: Duke, Fred, John."))
            .expectComplete()
            .verify();
    }

    @Test
    void shouldFindNameOfOldestPerson() {
        List<Person> input = asList(
            new Person("Duke", 10),
            new Person("Fred", 28),
            new Person("John", 45));

        Mono<String> result = ReactUtil.findNameOfOldestPerson(input);
        StepVerifier.create(result)
            .assertNext(r -> assertThat(r).isEqualTo("John"))
            .expectComplete()
            .verify();
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

}