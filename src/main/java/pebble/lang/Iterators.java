package pebble.lang;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Iterators {

  private Iterators() {}

  @NotNull
  public static <T> Iterator<@Nullable T> empty() {
    return Collections.emptyIterator();
  }

  @NotNull
  public static <T> Iterator<@Nullable T> singleton(@Nullable T value) {
    return new Iterator<>() {

      private boolean hasNext = true;

      @Override
      public boolean hasNext() {
        return this.hasNext;
      }

      @Override
      public T next() {
        if (!this.hasNext) {
          throw new NoSuchElementException();
        }

        this.hasNext = false;

        return null;
      }
    };
  }

  @NotNull
  @SafeVarargs
  public static <T> Iterator<@Nullable T> iterate(@Nullable T... values) {
    if (values == null) {
      return singleton(null);
    }

    return Arrays.asList(values).iterator();
  }

  @NotNull
  public static <T> Iterator<@Nullable T> iterate(
      @Nullable T initialValue,
      @NotNull UnaryOperator<T> operator,
      @NotNull Predicate<T> predicate) {
    Objects.requireNonNull(operator);
    Objects.requireNonNull(predicate);
    return new Iterator<>() {

      @Nullable
      private T nextValue = initialValue;

      private boolean firstTime;

      @Override
      public boolean hasNext() {
        return predicate.test(nextValue);
      }

      @Override
      public T next() {
        return this.nextValue;
      }
    };
  }

  @NotNull
  public static <T> Iterator<@Nullable T> repeat(@Nullable T value) {
    return new Iterator<>() {
      @Override
      public boolean hasNext() {
        return true;
      }

      @SuppressWarnings("java:S2272")
      @Override
      public T next() {
        return value;
      }
    };
  }

  @NotNull
  public static <T> Iterator<@Nullable T> repeat(@NotNull Supplier<@Nullable T> supplier) {
    return new Iterator<>() {
      @Override
      public boolean hasNext() {
        return true;
      }

      @SuppressWarnings("java:S2272")
      @Override
      public T next() {
        return supplier.get();
      }
    };
  }
}
