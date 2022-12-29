package pebble.data;

import static org.assertj.core.api.Assertions.assertThat;

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
}
