package pebble.function;

import java.util.Objects;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ExceptionalFunction<T, R> {

  @SuppressWarnings("java:S112")
  @Nullable
  R apply(@Nullable T t) throws Exception;

  @Nullable
  default <V> ExceptionalFunction<@Nullable T, @Nullable V> andThen(
      @NotNull Function<? super @Nullable R, ? extends @Nullable V> after) {
    Objects.requireNonNull(after);

    return t -> after.apply(this.apply(t));
  }

  @Nullable
  default <V> ExceptionalFunction<@Nullable V, @Nullable R> compose(
      @NotNull Function<? super @Nullable V, ? extends @Nullable T> before) {
    Objects.requireNonNull(before);

    return v -> this.apply(before.apply(v));
  }
}
