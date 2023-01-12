package pebble.function;

import java.util.function.Function;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ExceptionalFunction<T, R> {

  @SuppressWarnings("java:S112")
  @Nullable
  R apply(@Nullable T t) throws Exception;

  @Nullable
  default <V> ExceptionalFunction<@Nullable T, @Nullable V> andThen(
      Function<? super @Nullable R, ? extends @Nullable V> after) {
    return t -> after.apply(this.apply(t));
  }

  @Nullable
  default <V> ExceptionalFunction<@Nullable V, @Nullable R> compose(
      Function<? super @Nullable V, ? extends @Nullable T> before) {
    return v -> this.apply(before.apply(v));
  }
}
