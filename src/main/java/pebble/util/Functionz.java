package pebble.util;

import java.util.Objects;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Functionz {

  private Functionz() {}

  @NotNull
  public static <T, U> Function<@Nullable T, @Nullable U> safety(
      @NotNull Function<@NotNull T, @Nullable U> f) {
    Objects.requireNonNull(f);

    return t -> t == null ? null : f.apply(t);
  }
}
