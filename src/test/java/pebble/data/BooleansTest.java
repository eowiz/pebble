package pebble.data;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pebble.lang.Booleans;

/** A test class for {@link Booleans}. */
public class BooleansTest {

  @ParameterizedTest
  @CsvSource({"null,false", "true,true", "false,false"})
  void isTrue_Boolean(Boolean bool, boolean expected) {
    // act:
    final var actual = Booleans.isTrue(bool);

    // assert:
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({"true,true", "false,false"})
  void isTrue_boolean(boolean bool, boolean expected) {
    // act:
    final var actual = Booleans.isTrue(bool);

    // assert:
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({"null,false", "true,false", "false,true"})
  void isFalse_Boolean(Boolean bool, boolean expected) {
    // act:
    final var actual = Booleans.isFalse(bool);

    // assert:
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({"true,false", "false,true"})
  void isFalse_boolean(boolean bool, boolean expected) {
    // act:
    final var actual = Booleans.isFalse(bool);

    // assert:
    assertThat(actual).isEqualTo(expected);
  }
}
