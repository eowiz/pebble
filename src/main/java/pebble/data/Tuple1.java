package pebble.data;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pebble.util.Iteratorz;

@SuppressWarnings("java:S117")
public record Tuple1<T>(@Nullable T _1) implements Iterable<T> {

  @NotNull
  public static <T> Tuple1<@Nullable T> of(@Nullable T _1) {
    return new Tuple1<>(_1);
  }

  @NotNull
  public <U> Tuple1<@Nullable U> map(
      @NotNull Function<? super @Nullable T, ? extends @Nullable U> mapping) {
    Objects.requireNonNull(mapping);

    return new Tuple1<>(mapping.apply(this._1));
  }

  @NotNull
  @SuppressWarnings("unchecked")
  public <U> Tuple1<@Nullable U> flatMap(
      @NotNull Function<? super @Nullable T, Tuple1<? extends @Nullable U>> mapping) {
    Objects.requireNonNull(mapping);

    return (Tuple1<U>) mapping.apply(this._1);
  }

  @NotNull
  public <U> Tuple1<@Nullable U> replace(@Nullable U replacement) {
    return new Tuple1<>(replacement);
  }

  @NotNull
  public Tuple1<@Nullable T> replaceIfNull(@Nullable T replacement) {
    if (this._1 == null) {
      return new Tuple1<>(replacement);
    }

    return this;
  }

  public boolean test(@NotNull Predicate<@Nullable T> predicate) {
    Objects.requireNonNull(predicate);

    return predicate.test(_1);
  }

  public boolean contains(@NotNull Predicate<@Nullable T> predicate) {
    Objects.requireNonNull(predicate);

    return test(predicate);
  }

  @NotNull
  @Override
  public Iterator<@Nullable T> iterator() {
    return Iteratorz.singleton(this._1);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    Tuple1<?> that = (Tuple1<?>) object;
    return Objects.equals(this._1, that._1);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this._1);
  }
}
