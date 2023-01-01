package pebble.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.NoSuchElementException;
import java.util.function.Predicate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pebble.converter.OptionValue;

@SuppressWarnings("java:S5786")
public class OptionTest {

  @ParameterizedTest
  // @MethodSource("provider_of")
  @CsvSource(
      value = {
        "null | None       | false",
        "some | Some(some) | true",
        "null | Some(some) | false",
        "some | None       | false",
        "some | Some(1)    | false",
        "1    | Some(some) | false"
      },
      delimiter = '|')
  void of(Object object, @OptionValue Option<?> expected, boolean isEqual) {
    // act:
    final var actual = Option.of(object);

    // assert:
    assertThat(actual.equals(expected)).isEqualTo(isEqual);
  }

  @ParameterizedTest
  // spotless:off
  @CsvSource(
      value = {
          "None       | false",
          "Some(some) | true"
      },
      delimiter = '|')
    // spotless:on
  void isPresent(@OptionValue Option<?> option, boolean expected) {
    // act:
    final var actual = option.isPresent();

    // assert:
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  // spotless:off
  @CsvSource(
      value = {
          "None       | true",
          "Some(some) | false"
      },
      delimiter = '|')
    // spotless:on
  void isEmpty(@OptionValue Option<?> option, boolean expected) {
    // act:
    final var actual = option.isEmpty();

    // assert:
    assertThat(actual).isEqualTo(expected);
  }

  @Nested
  public class Get {

    @Test
    void get_some_null() {
      // arrange:
      final var option = Option.some(null);

      // act:
      final var actual = option.get();

      // assert:
      assertThat(actual).isNull();
    }

    @Test
    void get_some() {
      // arrange:
      final var some = Option.some(1);

      // act:
      final var actual = some.get();

      // assert:
      assertThat(actual).isEqualTo(1);
    }

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void get_none_throw_exception() {
      // arrange:
      final var none = Option.none();

      // act:
      final var actual = catchThrowable(() -> none.get());

      // assert:
      assertThat(actual).isInstanceOf(NoSuchElementException.class);
    }
  }

  @Test
  void fullToNone_null() {
    // arrange:
    final var some = Option.some(null);

    // act:
    final var actual = some.nullToNone();

    // assert:
    assertThat(actual).isEqualTo(Option.none());
  }

  @Test
  void nullToNone_some_not_null() {
    // arrange:
    final var option = Option.some(1);

    // arrange:
    final var actual = option.nullToNone();

    // assert:
    assertThat(actual).isNotEqualTo(Option.none());
  }

  @Test
  void map_none() {
    // arrange:
    final var none = Option.<String>none();

    // act:
    @SuppressWarnings("DataFlowIssue")
    final var actual = none.map(String::toUpperCase);

    // assert:
    assertThat(actual).isEqualTo(Option.none());
  }

  @Test
  void map_some() {
    // arrange:
    final var some = Option.some("some");

    // act:
    @SuppressWarnings("DataFlowIssue")
    final var actual = some.map(String::toUpperCase);

    // assert:
    assertThat(actual).isEqualTo(Option.some("SOME"));
  }

  @Test
  void flatMap_none() {
    // arrange:
    final var none = Option.<String>none();

    // act:
    @SuppressWarnings("DataFlowIssue")
    final var actual = none.flatMap(it -> Option.some(it.toUpperCase()));

    // assert:
    assertThat(actual).isEqualTo(Option.none());
  }

  @Test
  void flatMap_some_some() {
    // arrange:
    final var some = Option.some("some");

    // act:
    @SuppressWarnings("DataFlowIssue")
    final var actual = some.flatMap(it -> Option.some(it.toUpperCase()));

    // assert:
    assertThat(actual).isEqualTo(Option.some("SOME"));
  }

  @Test
  void flatMap_some_none() {
    // arrange:
    final var some = Option.some("some");

    // act:
    final var actual = some.flatMap(it -> Option.<String>none());

    // assert:
    assertThat(actual).isEqualTo(Option.none());
  }

  @Test
  void filter_none() {
    // arrange:
    final var none = Option.<String>none();

    // act:
    @SuppressWarnings("DataFlowIssue")
    final var actual = none.filter(it -> it.isBlank());

    // assert:
    assertThat(actual).isEqualTo(Option.none());
  }

  @Test
  void filter_some_false() {
    // arrange:
    final var some = Option.some("some");

    // act:
    @SuppressWarnings("DataFlowIssue")
    final var actual = some.filter(String::isBlank);

    // assert:
    assertThat(actual).isEqualTo(Option.none());
  }

  @Test
  void filter_some_true() {
    // arrange:
    final var some = Option.some("some");

    // act:
    @SuppressWarnings("DataFlowIssue")
    final var actual = some.filter(Predicate.not(String::isBlank));

    // assert:
    assertThat(actual).isEqualTo(Option.some("some"));
  }
}
