package pebble.function;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ExceptionalBiConsumer<T, U> {

  void accept(@Nullable T t, @Nullable U u) throws Exception;

  @NotNull
  default ExceptionalBiConsumer<@Nullable T, @Nullable U> andThen(
      @NotNull ExceptionalBiConsumer<? super @Nullable T, ? super @Nullable U> after) {
    Objects.requireNonNull(after);

    return (t, u) -> {
      this.accept(t, u);
      after.accept(t, u);
    };
  }
}
