package pebble.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record Tuple<T, U>(@Nullable T first, @Nullable U second) {

  public static <T, U> Tuple<@Nullable T, @Nullable U> of(@Nullable T first, @Nullable U second) {
    return new Tuple<>(first, second);
  }

  @Nullable
  public T getLeft() {
    return this.first();
  }

  @Nullable
  public U getRight() {
    return this.second();
  }

  @NotNull
  public Tuple<@Nullable U, @Nullable T> swap() {
    return new Tuple<>(this.second, this.first);
  }
}
