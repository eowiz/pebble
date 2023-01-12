package pebble.function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ExceptionalConsumer<T> {

  @SuppressWarnings("java:S112")
  void accept(@Nullable T t) throws Exception;

  @NotNull
  default ExceptionalConsumer<@Nullable T> andThen(
      @NotNull ExceptionalConsumer<@Nullable T> after) {
    return t -> {
      this.accept(t);
      after.accept(t);
    };
  }
}
