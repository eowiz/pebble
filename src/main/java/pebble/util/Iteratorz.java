package pebble.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Iteratorz {

  private static final Object NONE = new Object();

  private Iteratorz() {}

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

        return value;
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
      @NotNull Supplier<@Nullable T> seedSupplier, @NotNull UnaryOperator<@Nullable T> f) {
    Objects.requireNonNull(seedSupplier);
    Objects.requireNonNull(f);

    return new Iterator<>() {

      @SuppressWarnings("unchecked")
      private T value = (T) NONE;

      @Override
      public boolean hasNext() {
        return true;
      }

      @SuppressWarnings("java:S2272")
      @Override
      public T next() {
        this.value = (this.value == NONE) ? seedSupplier.get() : f.apply(this.value);

        return this.value;
      }
    };
  }

  @NotNull
  public static <T> Iterator<@Nullable T> iterate(
      @NotNull Supplier<@Nullable T> seedSupplier,
      @NotNull Predicate<? super @Nullable T> hasNext,
      @NotNull UnaryOperator<@Nullable T> next) {
    Objects.requireNonNull(hasNext);
    Objects.requireNonNull(next);

    return new Iterator<>() {

      private T prev;

      private boolean started;

      private boolean finished;

      private boolean nextCalled;

      @Override
      public boolean hasNext() {
        if (this.finished) {
          return false;
        }

        if (!nextCalled) {
          return !hasNext.test(this.prev);
        }

        final T value;
        if (!this.started) {
          value = seedSupplier.get();
          this.started = true;
        } else {
          value = next.apply(prev);
        }

        if (!hasNext.test(value)) {
          this.prev = null;
          this.finished = true;

          return false;
        }

        this.prev = value;

        return true;
      }

      @Nullable
      @Override
      public T next() {
        if (!this.hasNext()) {
          throw new NoSuchElementException();
        }

        this.nextCalled = true;

        return this.prev;
      }
    };
  }

  @NotNull
  public static <T> Iterator<@Nullable T> generate(@Nullable T seed) {
    return iterate(() -> seed, Function.<T>identity()::apply);
  }

  @NotNull
  public static <T> Iterator<@Nullable T> generate(@NotNull Supplier<@Nullable T> seedSupplier) {
    Objects.requireNonNull(seedSupplier);

    return iterate(seedSupplier, Function.<T>identity()::apply);
  }

  @NotNull
  public static <T> Iterable<@Nullable T> toIterable(@NotNull Iterator<@Nullable T> iterator) {
    Objects.requireNonNull(iterator);

    return () -> iterator;
  }
}
