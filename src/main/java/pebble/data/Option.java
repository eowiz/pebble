package pebble.data;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pebble.data.Option.None;
import pebble.data.Option.Some;
import pebble.util.Iteratorz;

/**
 * Alternative of {@link java.util.Optional}.
 *
 * <p>This class is inspired by {@code Option} in <a href="https://scala-lang.org/">Scala</a>. The
 * behavior is similar but not same.
 *
 * @param <T> The type of the optional value.
 * @author eowiz
 * @since 1.0.0
 */
@SuppressWarnings("java:S1610")
public sealed interface Option<T> extends Iterable<T> permits Some, None {

  /**
   * Returns {@code true}, if this is {@code None}, otherwise {@code false}.
   *
   * @return Returns {@code true}, if this is {@code None}, otherwise {@code false}
   */
  boolean isEmpty();

  /**
   * @return the value
   * @throws NoSuchElementException if this is a {@code None}.
   */
  @Nullable
  T get();

  @NotNull
  static <T> Option<@Nullable T> of(@Nullable T value) {
    return some(value);
  }

  @NotNull
  @SuppressWarnings("unchecked")
  static <T> Option<@Nullable T> none() {
    return (Option<T>) None.SINGLETON;
  }

  @NotNull
  static <T> Option<@Nullable T> some(@Nullable T value) {
    return new Some<>(value);
  }

  @NotNull
  default Optional<@NotNull T> asJava() {
    return isEmpty() ? Optional.empty() : Optional.ofNullable(get());
  }

  default boolean isPresent() {
    return !this.isEmpty();
  }

  @NotNull
  default Option<@Nullable T> nullToNone() {
    return (this.isEmpty() || this.get() != null) ? this : none();
  }

  @NotNull
  default <U> Option<@Nullable U> map(
      @NotNull Function<? super @Nullable T, ? extends @Nullable U> mapping) {
    Objects.requireNonNull(mapping);

    return this.isEmpty() ? none() : some(mapping.apply(this.get()));
  }

  @NotNull
  @SuppressWarnings("unchecked")
  default <U> Option<@Nullable U> flatMap(
      @NotNull Function<? super T, Option<? extends U>> mapping) {
    Objects.requireNonNull(mapping);

    return this.isEmpty() ? none() : (Option<U>) mapping.apply(this.get());
  }

  @NotNull
  default Option<@Nullable T> filter(@NotNull Predicate<@Nullable T> predicate) {
    Objects.requireNonNull(predicate);

    if (this.isEmpty()) {
      return this;
    }

    return predicate.test(this.get()) ? this : none();
  }

  @NotNull
  default Option<@Nullable T> filterNot(@NotNull Predicate<@Nullable T> predicate) {
    Objects.requireNonNull(predicate);

    return this.filter(Predicate.not(predicate));
  }

  @Nullable
  @Contract(value = "null -> null; !null -> !null", pure = true)
  default T orElse(@Nullable T value) {
    return this.isEmpty() ? value : this.get();
  }

  @Nullable
  default T orElseGet(@NotNull Supplier<@Nullable T> supplier) {
    Objects.requireNonNull(supplier);

    return this.isEmpty() ? supplier.get() : this.get();
  }

  @NotNull
  default Option<@Nullable T> or(@NotNull Option<@Nullable T> another) {
    Objects.requireNonNull(another);

    return this.isEmpty() ? another : this;
  }

  @Nullable
  default <X extends Throwable> T orElseThrow(
      @NotNull Supplier<? extends @NotNull X> exceptionSupplier) throws X {
    Objects.requireNonNull(exceptionSupplier);

    if (this.isEmpty()) {
      throw exceptionSupplier.get();
    }

    return this.get();
  }

  @Nullable
  default T orNull() {
    return this.isPresent() ? this.get() : null;
  }

  default boolean exists(@NotNull Predicate<@Nullable T> predicate) {
    Objects.requireNonNull(predicate);

    return !this.isEmpty() && predicate.test(this.get());
  }

  default boolean forall(@NotNull Predicate<@Nullable T> predicate) {
    Objects.requireNonNull(predicate);

    return this.isEmpty() || predicate.test(this.get());
  }

  @NotNull
  default <U> Option<Tuple2<@Nullable T, @Nullable U>> zip(@Nullable U right) {
    return this.isEmpty() ? none() : some(new Tuple2<>(this.get(), right));
  }

  @NotNull
  default Stream<@Nullable T> stream() {
    return isEmpty() ? Stream.empty() : Stream.ofNullable(this.get());
  }

  final class None<T> implements Option<T> {

    private static final None<?> SINGLETON = new None<>();

    private None() {}

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    public T get() {
      throw new NoSuchElementException("No value present");
    }

    /**
     * Returns an empty iterator.
     *
     * @return an empty iterator
     */
    @NotNull
    @Override
    public Iterator<@Nullable T> iterator() {
      return Collections.emptyIterator();
    }

    @NotNull
    @Override
    public String toString() {
      return "None";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
      return obj == this;
    }

    @Override
    public int hashCode() {
      return super.hashCode();
    }
  }

  final class Some<T> implements Option<T> {

    @Nullable private final T value;

    private Some(@Nullable T value) {
      this.value = value;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Nullable
    @Override
    public T get() {
      return this.value;
    }

    @NotNull
    @Override
    public Iterator<@Nullable T> iterator() {
      return Iteratorz.iterate(this.value);
    }

    @NotNull
    @Override
    public String toString() {
      if (this.value instanceof String) {
        return "Some(\"" + this.value + "\")";
      }

      return "Some(" + this.value + ")";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
      return this == obj
          || (obj instanceof Option.Some<?> && Objects.equals(this.value, ((Some<?>) obj).value));
    }

    @Override
    public int hashCode() {
      return Objects.hash(value);
    }
  }
}
