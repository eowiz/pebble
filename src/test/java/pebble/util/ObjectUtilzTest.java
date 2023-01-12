package pebble.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class ObjectUtilzTest {

  @Nested
  class AnyNull {

    @Test
    void direct_one_null_argument() {
      // act:
      // In most practice case, the type of the argument of ObjectUtils#anyNull is typed as Object
      // or Supplier<?>.
      final var actual = ObjectUtilz.anyNull((Object) null);

      // assert:
      assertThat(actual).isTrue();
    }

    @Test
    void direct_one_non_null_argument() {
      // act:
      final var actual = ObjectUtilz.anyNull("non-null");

      // assert:
      assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @MethodSource("provider_objects")
    void object(Object[] objects, boolean expected) {
      // act:
      final var actual = ObjectUtilz.anyNull(objects);

      // assert:
      assertThat(actual).isEqualTo(expected);
    }

    static List<Arguments> provider_objects() {
      return List.of(
          arguments(new Object[] {}, false),
          arguments(new Object[] {null}, true),
          arguments(new Object[] {"non-null"}, false),
          arguments(new Object[] {null, null}, true),
          arguments(new Object[] {"non-null", null}, true),
          arguments(new Object[] {null, "non-null"}, true),
          arguments(new Object[] {"non-null", "non-null"}, false));
    }

    @ParameterizedTest
    @MethodSource("provider_supplier")
    void supplier(Supplier<Object>[] suppliers, boolean expected) {
      // act:
      final var actual = ObjectUtilz.anyNull(suppliers);

      // assert:
      assertThat(actual).isEqualTo(expected);
    }

    static List<Arguments> provider_supplier() {
      return List.of(
          arguments(new Supplier<?>[] {}, false),
          arguments(new Supplier<?>[] {() -> null}, true),
          arguments(new Supplier<?>[] {() -> null, () -> null}, true),
          arguments(new Supplier<?>[] {() -> null, () -> "non-null"}, true),
          arguments(new Supplier<?>[] {() -> "non-null"}, false),
          arguments(new Supplier<?>[] {() -> "non-null", () -> "non-null"}, false));
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConfusingArgumentToVarargsMethod"})
    @Test
    void mock_direct_null_argument() {
      try (MockedStatic<ObjectUtilz> utils = Mockito.mockStatic(ObjectUtilz.class)) {
        // arrange:
        utils.when(() -> ObjectUtilz.anyNull((Supplier<Object>[]) null)).thenReturn(true);

        // act:
        // In most practice case, the type of the argument of ObjectUtils#anyNull is typed as Object
        // or Supplier<?>.
        final var actual = ObjectUtilz.anyNull(null);

        // assert:
        assertThat(actual).isTrue();
        utils.verify(() -> ObjectUtilz.anyNull((Supplier<Object>[]) null), times(1));
        utils.verify(() -> ObjectUtilz.anyNull((Object[]) null), times(0));
      }
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantValue"})
    @Test
    void mock_indirect_null_argument_as_object() {
      try (MockedStatic<ObjectUtilz> utils = Mockito.mockStatic(ObjectUtilz.class)) {
        // arrange:
        Object nullObj = null;
        utils.when(() -> ObjectUtilz.anyNull(nullObj)).thenReturn(true);

        // act:
        final var actual = ObjectUtilz.anyNull(nullObj);

        // assert:
        assertThat(actual).isTrue();
        utils.verify(() -> ObjectUtilz.anyNull((Supplier<Object>[]) null), times(0));
        utils.verify(() -> ObjectUtilz.anyNull((Object[]) null), times(1));
      }
    }
  }
}
