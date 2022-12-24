package pebble.data;

import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Lazy<T> implements Supplier<T> {

  @NotNull
  private final Supplier<@Nullable T> supplier;

  @Nullable
  private T value;

  private boolean getCalled;

  private Lazy(@NotNull final Supplier<@Nullable T> supplier) {
    this.supplier = supplier;
  }

  @Nullable
  @Override
  public T get() {
    if (!getCalled) {
      this.value = supplier.get();
      this.getCalled = true;
    }

    return this.value;
  }

  @NotNull
  public static <T> Lazy<T> of(@NotNull final Supplier<@Nullable T> supplier) {
    return new Lazy<>(supplier);
  }

  @NotNull
  public <U> Lazy<U> map(@NotNull final Function<? super @Nullable T, ? extends @Nullable U> mapping) {
    return Lazy.of(() -> mapping.apply(this.value));
  }

  @NotNull
  @SuppressWarnings("unchecked")
  public <U> Lazy<U> flatMap(@NotNull
      final Function<? super @Nullable T, @NotNull Lazy<? extends @Nullable U>> mapping) {
    return (Lazy<U>) mapping.apply(this.value);
  }
}
