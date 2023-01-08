package pebble.data;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @param <T>
 * @see <a
 *     href="https://github.com/spring-projects/spring-data-commons/blob/3.0.0/src/main/java/org/springframework/data/util/Lazy.java">Lazy.java</a>
 */
public final class Lazy<T> implements Supplier<T> {

  @NotNull private final Supplier<? extends @Nullable T> supplier;

  @Nullable private T value;

  private boolean getCalled;

  private Lazy(@NotNull final Supplier<? extends @Nullable T> supplier) {
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
  public static <T> Lazy<@Nullable T> of(@NotNull final Supplier<@Nullable T> supplier) {
    return new Lazy<>(supplier);
  }

  @NotNull
  public <U> Lazy<@Nullable U> map(
      @NotNull final Function<? super @Nullable T, ? extends @Nullable U> mapping) {
    return Lazy.of(() -> mapping.apply(this.value));
  }

  @NotNull
  @SuppressWarnings("unchecked")
  public <U> Lazy<@Nullable U> flatMap(
      @NotNull final Function<? super @Nullable T, @NotNull Lazy<? extends @Nullable U>> mapping) {
    return (Lazy<U>) mapping.apply(this.value);
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Lazy<?>)) {
      return false;
    }

    @SuppressWarnings("PatternVariableCanBeUsed")
    final var lazy = (Lazy<?>) obj;

    if (this.getCalled != lazy.getCalled) {
      return false;
    }

    if (!Objects.equals(this.supplier, lazy.supplier)) {
      return false;
    }

    return Objects.equals(value, lazy.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.supplier, this.getCalled, this.value);
  }
}
