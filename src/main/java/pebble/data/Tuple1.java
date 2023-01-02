package pebble.data;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pebble.lang.Iteratorz;

@SuppressWarnings("java:S117")
public record Tuple1<T>(@Nullable T _1) implements Iterable<T> {

  @NotNull
  public <U> Tuple1<@Nullable U> map(@NotNull Function<? super T, ? extends U> mapping) {
    return new Tuple1<>(mapping.apply(_1));
  }

  @NotNull
  @SuppressWarnings("unchecked")
  public <U> Tuple1<@Nullable U> flatMap(
      @NotNull Function<? super T, Tuple1<? extends U>> mapping) {
    return (Tuple1<U>) mapping.apply(_1);
  }

  @NotNull
  public <U> Tuple1<@Nullable U> replace(@Nullable U replacement) {
    return new Tuple1<>(replacement);
  }

  @NotNull
  public Tuple1<@Nullable T> replaceIfNull(@Nullable T replacement) {
    if (_1 == null) {
      return new Tuple1<>(replacement);
    }

    return this;
  }

  public boolean test(@NotNull Predicate<@Nullable T> predicate) {
    return predicate.test(_1);
  }

  public boolean contains(@NotNull Predicate<@Nullable T> predicate) {
    return test(predicate);
  }

  @NotNull
  @Override
  public Iterator<T> iterator() {
    return Iteratorz.singleton(_1);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tuple1<?> value = (Tuple1<?>) o;
    return Objects.equals(_1, value._1);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_1);
  }
}
