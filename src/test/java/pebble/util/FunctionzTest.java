package pebble.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import java.util.function.Function;
import org.junit.jupiter.api.Test;

@SuppressWarnings("java:S5786")
public class FunctionzTest {

  @Test
  void safety_throws_npe() {
    // act:
    @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
    final var actual = catchThrowable(() -> Functionz.safety(null));

    // assert:
    assertThat(actual).isInstanceOf(NullPointerException.class);
  }

  @Test
  void safety_apply_to_full() {
    // arrange:
    Function<Integer, String> f = Object::toString;

    // act:
    final var actualSafety = Functionz.safety(f);
    final var actual = actualSafety.apply(null);

    // assert:
    assertThat(actual).isNull();
  }
}
