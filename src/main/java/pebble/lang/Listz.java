package pebble.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Listz {

  private Listz() {}

  public static <T> List<T> repeat(
      int n, @NotNull T seed, @NotNull UnaryOperator<@NotNull T> generator) {
    return repeat(n, generator.apply(seed));
  }

  @NotNull
  public static <T> List<T> repeat(int n, @Nullable T t) {
    return repeat(n, () -> t);
  }

  @NotNull
  public static <T> List<T> repeat(int n, @NotNull Supplier<@Nullable T> supplier) {
    final var list = new ArrayList<T>(n);
    for (int i = 0; i < n; i++) {
      list.set(i, supplier.get());
    }

    return Collections.unmodifiableList(list);
  }
}
