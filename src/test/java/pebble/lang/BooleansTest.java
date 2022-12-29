package pebble.lang;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.invoke.MethodHandles;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** A test class for {@link Booleans}. */
public class BooleansTest {

  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @ParameterizedTest
  @CsvSource(
      value = {" | false", "true | true", "false | false"},
      delimiter = '|')
  void isTrue_Boolean(Boolean bool, boolean expected) {
    // arrange:
    log.info("bool = {}", bool);
    log.info("expected = {}", expected);

    // act:
    final var actual = Booleans.isTrue(bool);

    // assert:
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource(
      value = {"true | true", "false| false"},
      delimiter = '|')
  void isTrue_boolean(boolean bool, boolean expected) {
    // act:
    final var actual = Booleans.isTrue(bool);

    // assert:
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource(
      value = {" | false", "true | false", "false | true"},
      delimiter = '|')
  void isFalse_Boolean(Boolean bool, boolean expected) {
    // act:
    final var actual = Booleans.isFalse(bool);

    // assert:
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource(
      value = {"true | false", "false | true"},
      delimiter = '|')
  void isFalse_boolean(boolean bool, boolean expected) {
    // act:
    final var actual = Booleans.isFalse(bool);

    // assert:
    assertThat(actual).isEqualTo(expected);
  }
}
