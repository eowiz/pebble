package pebble.data;

import org.jetbrains.annotations.Nullable;

public final class Tuple<T, U> {

  @Nullable private final T first;

  @Nullable private final U second;

  public Tuple(@Nullable T first, @Nullable U second) {
    this.first = first;
    this.second = second;
  }

  public static <T, U> Tuple<@Nullable T, @Nullable U> of(@Nullable T first, @Nullable U second) {
    return new Tuple<>(first, second);
  }

  @Nullable
  public T getFirst() {
    return this.first;
  }

  @Nullable
  public T getLeft() {
    return this.getFirst();
  }

  @Nullable
  public U getSecond() {
    return this.second;
  }

  @Nullable
  public U getRight() {
    return this.getSecond();
  }
}
