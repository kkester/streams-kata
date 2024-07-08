package io.pivotal.streamskata.webflux;

import io.pivotal.streamskata.Person;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class WebfluxUtilTest {
    
    @Test
    void shouldMapStringsToUpperCase() {
        Flux<String> result = WebfluxUtil.mapToUppercase("This", "is", "java", "8");
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
        Flux<String> result = WebfluxUtil.removeElementsWithMoreThanFourCharacters("This", "is", "java", "8");
        StepVerifier.create(result)
            .consumeNextWith(v -> assertThat(v).isEqualTo("is"))
            .consumeNextWith(v -> assertThat(v).isEqualTo("8"))
            .expectComplete()
            .verify();
    }

    @Test
    void shouldSortStrings() {
        Flux<String> result = WebfluxUtil.sortStrings("C", "F", "A", "D", "B", "E");
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
        Flux<Integer> result = WebfluxUtil.sortIntegers("2", "4", "12", "3");
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
        Flux<Integer> result = WebfluxUtil.sortIntegersDescending("2", "4", "12", "3");
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
        Mono<Integer> result = WebfluxUtil.sum(1, 2, 3, 4, 5);
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

        Mono<String> result = WebfluxUtil.separateNamesByComma(input);
        StepVerifier.create(result)
            .assertNext(r -> assertThat(r).isEqualTo("Names: Duke, Fred, John."))
            .expectComplete()
            .verify();
    }

    @Test
    void shouldFindNameOfOldestPerson() {
        List<Person> input = asList(
            new Person("Duke", 10),
            new Person("John", 45),
            new Person("Fred", 28));

        Mono<String> result = WebfluxUtil.findNameOfOldestPerson(input);
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
            new Person("Barry", 18),
            new Person("Marius", 17));

        Flux<String> result = WebfluxUtil.filterPeopleLessThan18YearsOld(input);
        StepVerifier.create(result)
            .assertNext(r -> assertThat(r).isEqualTo("Duke"))
            .assertNext(r -> assertThat(r).isEqualTo("Marius"))
            .expectComplete()
            .verify();
    }

    @Test
    void shouldCreateAndSaveToken() {
        Mono<String> result = WebfluxUtil.generateAndSaveToken();
        StepVerifier.create(result)
            .assertNext(r -> assertThat(r).isEqualTo(WebfluxUtil.tokens.get(0)))
            .expectComplete()
            .verify();
    }

    @Test
    void shouldAddYoungestAndOldestToResultsOnSubscribe() throws Exception {
        List<Person> input = asList(
            new Person("Duke", 10),
            new Person("Fred", 28),
            new Person("John", 45),
            new Person("Marius", 17));
        Map<String, String> results = new HashMap<>();
        WebfluxUtil.addYoungestAndOldestToResults(input, results);
        awaitResults(results, Duration.ofMillis(100));
    }

    private void awaitResults(Map<String, String> results, Duration duration) throws Exception {
        if (results.size() != 2) {
            Mono.delay(Duration.ZERO).block();
            awaitResults(results, duration.minusMillis(1));
        }
        assertThat(results).containsEntry("oldest", "John");
        assertThat(results).containsEntry("youngest", "Duke");
    }
}