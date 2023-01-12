package pebble.util;

import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Predicatez {

  private Predicatez() {}

  @SuppressWarnings("java:S100")
  @NotNull
  public static <T> Predicate<@Nullable T> CONST(boolean bool) {
    return ignore -> bool;
  }

  @NotNull
  public static <T> Predicate<@Nullable T> alwaysTrue() {
    return ignore -> true;
  }

  @NotNull
  public static <T> Predicate<@Nullable T> alwaysFalse() {
    return ignore -> false;
  }
}
