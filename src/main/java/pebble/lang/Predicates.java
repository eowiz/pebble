package pebble.lang;

import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public final class Predicates {

  private Predicates() {}

  @SuppressWarnings("java:S100")
  @NotNull
  public static <T> Predicate<T> CONST(boolean bool) {
    return ignore -> bool;
  }

  @NotNull
  public static <T> Predicate<T> alwaysTrue() {
    return ignore -> true;
  }

  @NotNull
  public static <T> Predicate<T> alwaysFalse() {
    return ignore -> false;
  }
}
