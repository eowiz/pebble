package pebble.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("java:S5786")
public class PredicatezTest {

  @ParameterizedTest
  @MethodSource("provider_CONST_true")
  void CONST_true(Object object) {
    // arrange:
    final var CONST = Predicatez.CONST(true);

    // act:
    final var actual = CONST.test(object);

    // assert:
    assertThat(actual).isTrue();
  }

  static List<Arguments> provider_CONST_true() {
    return List.of(
        arguments((Object) null), arguments(new Object()), arguments(true), arguments(false));
  }

  @ParameterizedTest
  @MethodSource("provider_CONST_false")
  void CONST_false(Object object) {
    // arrange:
    final var CONST = Predicatez.CONST(false);

    // act:
    final var actual = CONST.test(object);

    // assert:
    assertThat(actual).isFalse();
  }

  static List<Arguments> provider_CONST_false() {
    return List.of(
        arguments((Object) null), arguments(new Object()), arguments(true), arguments(false));
  }

  @ParameterizedTest
  @MethodSource("provider_alwaysTrue")
  void alwaysTrue(Object object) {
    // arrange:

    // act:
    final var actual = Predicatez.alwaysTrue().test(object);

    // assert:
    assertThat(actual).isTrue();
  }

  static List<Arguments> provider_alwaysTrue() {
    return List.of(
        arguments((Object) null), arguments(new Object()), arguments(true), arguments(false));
  }

  @ParameterizedTest
  @MethodSource("provider_alwaysFalse")
  void alwaysFalse(Object object) {
    // arrange:

    // act:
    final var actual = Predicatez.alwaysFalse().test(object);

    // assert:
    assertThat(actual).isFalse();
  }

  static List<Arguments> provider_alwaysFalse() {
    return List.of(
        arguments((Object) null), arguments(new Object()), arguments(true), arguments(false));
  }
}
