package pebble.lang;

import org.jetbrains.annotations.Nullable;

public final class Booleans {

  private Booleans() {}

  public static boolean isTrue(@Nullable Boolean bool) {
    return Boolean.TRUE.equals(bool);
  }

  public static boolean isTrue(boolean bool) {
    return bool;
  }

  public static boolean isFalse(@Nullable Boolean bool) {
    return Boolean.FALSE.equals(bool);
  }

  public static boolean isFalse(boolean bool) {
    return !bool;
  }
}
