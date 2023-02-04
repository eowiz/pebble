package pebble.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.lang.invoke.MethodHandles;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** A test class for {@link Booleanz}. */
@SuppressWarnings("java:S5786")
public class BooleanzTest {

  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @ParameterizedTest
  @MethodSource("provider_isTrue_Boolean")
  void isTrue_Boolean(Boolean bool, boolean expected) {
    // arrange:
    log.info("bool = {}", bool);
    log.info("expected = {}", expected);

    // act:
    final var actual = Booleanz.isTrue(bool);

    // assert:
    assertThat(actual).isEqualTo(expected);
  }

  static List<Arguments> provider_isTrue_Boolean() {
    return List.of(
        arguments(null, false), arguments(Boolean.TRUE, true), arguments(Boolean.FALSE, false));
  }

  @ParameterizedTest
  @MethodSource("provider_isTrue_boolean")
  void isTrue_boolean(boolean bool, boolean expected) {
    // act:
    final var actual = Booleanz.isTrue(bool);

    // assert:
    assertThat(actual).isEqualTo(expected);
  }

  static List<Arguments> provider_isTrue_boolean() {
    return List.of(arguments(true, true), arguments(false, false));
  }

  @ParameterizedTest
  @MethodSource("provider_isFalse_Boolean")
  void isFalse_Boolean(Boolean bool, boolean expected) {
    // act:
    final var actual = Booleanz.isFalse(bool);

    // assert:
    assertThat(actual).isEqualTo(expected);
  }

  static List<Arguments> provider_isFalse_Boolean() {
    return List.of(arguments(null, false), arguments(true, false), arguments(false, true));
  }

  @ParameterizedTest
  @MethodSource("provider_isFalse_boolean")
  void isFalse_boolean(boolean bool, boolean expected) {
    // act:
    final var actual = Booleanz.isFalse(bool);

    // assert:
    assertThat(actual).isEqualTo(expected);
  }

  static List<Arguments> provider_isFalse_boolean() {
    return List.of(arguments(true, false), arguments(false, true));
  }
}
