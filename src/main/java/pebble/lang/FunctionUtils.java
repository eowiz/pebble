package pebble.lang;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FunctionUtils {

  private FunctionUtils() {}

  public static <T, U> Function<@Nullable T, @Nullable U> safety(
      Function<@NotNull T, @Nullable U> f) {
    return t -> t == null ? null : f.apply(t);
  }
}
