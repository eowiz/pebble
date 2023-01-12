package pebble.function;

import java.util.Objects;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ExceptionalBiFunction<T, U, R> {

  @SuppressWarnings("java:S112")
  @Nullable
  R apply(@Nullable T t, @Nullable U u) throws Exception;

  @NotNull
  default <V> ExceptionalBiFunction<@Nullable T, @Nullable U, @Nullable V> andThen(
      @NotNull Function<? super @Nullable R, ? extends @Nullable V> after) {
    Objects.requireNonNull(after);

    return (t, u) -> after.apply(this.apply(t, u));
  }
}
