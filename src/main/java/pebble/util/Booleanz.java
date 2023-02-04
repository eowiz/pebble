package pebble.util;

import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

public final class Booleanz {

  private Booleanz() {}

  /**
   * Checks if a {@link Boolean} value is {@code true}, handling {@code null} by returning {@code
   * false}.
   *
   * @see <a
   *     href="https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/BooleanUtils.html#isTrue-java.lang.Boolean-">BooleanUtils#isTrue(java.lang.Boolean)</a>
   * @param bool the boolean to check, {@code null} returns {@code false}
   * @return {@code true} only if the input is non-null and {@code true}
   */
  public static boolean isTrue(@Nullable Boolean bool) {
    return Boolean.TRUE.equals(bool);
  }

  /**
   * Checks if a Boolean value is true, handling {@code null} by returning {@code false}.
   *
   * @apiNote This method exists to be used as a {@link Predicate}, {@code filter(Booleanz::isTrue)}
   * @param bool the primitive boolean to check
   * @return true only if the input is non-null and true
   */
  public static boolean isTrue(boolean bool) {
    return bool;
  }

  /**
   * Checks if a {@link Boolean} value is {@code false}, handling {@code null} by returning {@code
   * true}.
   *
   * @see <a
   *     href="https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/BooleanUtils.html#isFalse-java.lang.Boolean-">BooleanUtils#isFalse(java.lang.Boolean)</a>
   * @param bool the boolean to check, {@code null} returns {@code false}
   * @return {@code true} only if the input is non-null and {@code false}
   */
  public static boolean isFalse(@Nullable Boolean bool) {
    return Boolean.FALSE.equals(bool);
  }

  public static boolean isFalse(boolean bool) {
    return !bool;
  }
}
