package pebble.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OptionTest {

  @ParameterizedTest(name = "Option.of({0}).equals({1}) = {2}")
  @MethodSource("provider_of")
  void of(Object object, Option<Object> expected, boolean isEqual) {
    // act:
    final var actual = Option.of(object);

    // assert:
    assertThat(actual.equals(expected)).isEqualTo(isEqual);
  }

  static List<Arguments> provider_of() {
    return List.of(
        arguments(null, Option.none(), true),
        arguments("some", Option.some("some"), true),
        arguments(null, Option.some("some"), false),
        arguments("some", Option.none(), false),
        arguments("some", Option.some(1), false),
        arguments("1", Option.some("some"), false));
  }
}
