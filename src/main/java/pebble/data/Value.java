package pebble.data;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pebble.lang.Iterators;

public record Value<T>(@Nullable T unwrap) implements Iterable<T> {

  @NotNull
  public <U> Value<@Nullable U> map(@NotNull Function<? super T, ? extends U> mapping) {
    return new Value<>(mapping.apply(unwrap));
  }

  @NotNull
  @SuppressWarnings("unchecked")
  public <U> Value<@Nullable U> flatMap(@NotNull Function<? super T, Value<? extends U>> mapping) {
    return (Value<U>) mapping.apply(unwrap);
  }

  @NotNull
  public <U> Value<@Nullable U> replace(@Nullable U replacement) {
    return new Value<>(replacement);
  }

  @NotNull
  public Value<@Nullable T> replaceIfNull(@Nullable T replacement) {
    if (unwrap == null) {
      return new Value<>(replacement);
    }

    return this;
  }

  public boolean test(@NotNull Predicate<@Nullable T> predicate) {
    return predicate.test(unwrap);
  }

  @NotNull
  @Override
  public Iterator<T> iterator() {
    return Iterators.singleton(unwrap);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Value<?> value = (Value<?>) o;
    return Objects.equals(unwrap, value.unwrap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unwrap);
  }
}
