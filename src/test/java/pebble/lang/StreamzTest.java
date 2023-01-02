package pebble.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@SuppressWarnings("java:S5786")
@ExtendWith(SoftAssertionsExtension.class)
public class StreamzTest {

  @InjectSoftAssertions private SoftAssertions softly;

  @Test
  void from_throws_npe() {
    // act:
    @SuppressWarnings("ConstantConditions")
    final var actual = catchThrowable(() -> Streamz.from(null));

    // assert:
    assertThat(actual).isInstanceOf(NullPointerException.class);
  }

  @Test
  void from_fixed_size_iterator() {
    // arrange:
    final var iterator = Iteratorz.iterate(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    // act:
    final var actual = Streamz.from(iterator);
    final var actualList = actual.toList();

    // arrange:
    softly.assertThat(actualList).hasSize(10);
    softly.assertThat(actualList).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
  }

  @Test
  void from_containing_null() {
    // arrange:
    final var iterator = Iteratorz.iterate(1, 2, null, 4, null);

    // act:
    final var actual = Streamz.from(iterator);
    final var actualList = actual.toList();

    // assert:
    softly.assertThat(actualList).hasSize(5);
    softly.assertThat(actualList).containsExactly(1, 2, null, 4, null);
  }

  @Test
  void from_infinite_iterator() {
    // arrange:
    final var iterator = Iteratorz.generate(1);

    // act:
    final var actual = Streamz.from(iterator);
    final var actualList = actual.limit(10).toList();

    // assert:
    softly.assertThat(actualList).hasSize(10);
    softly.assertThat(actualList).containsExactly(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
  }
}
