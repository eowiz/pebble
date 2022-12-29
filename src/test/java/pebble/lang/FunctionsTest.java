package pebble.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import java.util.function.Function;
import org.junit.jupiter.api.Test;

public class FunctionsTest {

  @Test
  public void safety_throws_npe() {
    // act:
    final var actual = catchThrowable(() -> Functions.safety(null));

    // assert:
    assertThat(actual).isInstanceOf(NullPointerException.class);
  }

  @Test
  public void safety_apply_to_full() {
    // arrange:
    Function<Integer, String> f = Object::toString;

    // act:
    final var actualSafety = Functions.safety(f);
    final var actual = actualSafety.apply(null);

    // assert:
    assertThat(actual).isNull();
  }
}
