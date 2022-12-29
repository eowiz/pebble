package pebble.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
public class StreamsTest {

  @InjectSoftAssertions private SoftAssertions softly;

  @Test
  public void from_throws_npe() {
    // act:
    final var actual = catchThrowable(() -> Streams.from(null));

    // assert:
    assertThat(actual).isInstanceOf(NullPointerException.class);
  }

  @Test
  public void from_fixed_size_iterator() {
    // arrange:
    final var iterator = Iterators.iterate(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    // act:
    final var actual = Streams.from(iterator);
    final var actualList = actual.toList();

    // arrange:
    softly.assertThat(actualList).hasSize(10);
    softly.assertThat(actualList).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
  }

  @Test
  public void from_containing_null() {
    // arrange:
    final var iterator = Iterators.iterate(1, 2, null, 4, null);

    // act:
    final var actual = Streams.from(iterator);
    final var actualList = actual.toList();

    // assert:
    softly.assertThat(actualList).hasSize(5);
    softly.assertThat(actualList).containsExactly(1, 2, null, 4, null);
  }

  @Test
  public void from_infinite_iterator() {
    // arrange:
    final var iterator = Iterators.generate(1);

    // act:
    final var actual = Streams.from(iterator);
    final var actualList = actual.limit(10).toList();

    // assert:
    softly.assertThat(actualList).hasSize(10);
    softly.assertThat(actualList).containsExactly(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
  }
}
