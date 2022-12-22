package pebble.lang;

import java.util.Comparator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ComparatorUtils {

  private static final int EQUAL = 0;

  private static final int HIGHER = 1;

  private static final int LOWER = -1;

  private ComparatorUtils() {}

  public static <T> int nullHighCompare(
      @Nullable T a, @Nullable T b, @NotNull Comparator<@Nullable T> comparator) {
    if (a == null && b == null) {
      return EQUAL;
    }

    if (a == null) {
      return HIGHER;
    }

    if (b == null) {
      return LOWER;
    }

    return comparator.compare(a, b);
  }

  public static <T> int nullLowCompare(
      @Nullable T a, @Nullable T b, @NotNull Comparator<@Nullable T> comparator) {
    return -nullHighCompare(a, b, comparator);
  }
}
