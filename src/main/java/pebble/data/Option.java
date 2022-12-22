package pebble.data;

import pebble.lang.IteratorUtils;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Alternative of {@link java.util.Optional}.
 *
 * <p>This class is inspired by {@code Option} in <a href="https://scala-lang.org/">Scala</a>. The
 * behavior is similar but not same.
 *
 * @param <T> The type of the optional value.
 * @since 1.0.0
 * @author eowiz
 */
@SuppressWarnings("java:S1610")
public abstract class Option<T> implements Iterable<T> {

  private Option() {}

  /**
   * Returns {@code true}, if this is {@code None}, otherwise {@code false}.
   *
   * @return Returns {@code true}, if this is {@code None}, otherwise {@code false}
   */
  public abstract boolean isEmpty();

  /**
   * @return the value
   * @throws NoSuchElementException if this is a {@code None}.
   */
  @Nullable
  public abstract T get();

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
  public final Optional<@NotNull T> toOptional() {
    return isEmpty() ? Optional.empty() : Optional.ofNullable(get());
  }

  public final boolean isPresent() {
    return !this.isEmpty();
  }

  public final Option<T> nullToNone() {
    return (this.isEmpty() || this.get() != null) ? this : none();
  }

  @NotNull
  public final <U> Option<@Nullable U> map(@NotNull Function<? super T, ? extends U> mapping) {
    Objects.requireNonNull(mapping);

    return this.isEmpty() ? none() : some(mapping.apply(this.get()));
  }

  @NotNull
  @SuppressWarnings("unchecked")
  public final <U> Option<@Nullable U> flatMap(
      @NotNull Function<? super T, Option<? extends U>> mapping) {
    Objects.requireNonNull(mapping);

    return this.isEmpty() ? none() : (Option<U>) mapping.apply(this.get());
  }

  @NotNull
  public final Option<@Nullable T> filter(@NotNull Predicate<T> predicate) {
    Objects.requireNonNull(predicate);

    if (this.isEmpty()) {
      return this;
    }

    return predicate.test(this.get()) ? this : none();
  }

  @NotNull
  public final Option<@Nullable T> filterNot(@NotNull Predicate<T> predicate) {
    Objects.requireNonNull(predicate);

    return this.filter(Predicate.not(predicate));
  }

  @Nullable
  public final T orElse(@Nullable T value) {
    return this.isEmpty() ? value : this.get();
  }

  @Nullable
  public final T orElseGet(@NotNull Supplier<@Nullable T> supplier) {
    Objects.requireNonNull(supplier);

    return this.isEmpty() ? supplier.get() : this.get();
  }

  @NotNull
  public final Option<@Nullable T> or(@NotNull Option<T> another) {
    Objects.requireNonNull(another);

    return this.isEmpty() ? another : this;
  }

  @Nullable
  public final <X extends Throwable> T orElseThrow(@NotNull Supplier<? extends X> exceptionSupplier)
      throws X {
    Objects.requireNonNull(exceptionSupplier);

    if (this.isEmpty()) {
      throw exceptionSupplier.get();
    }

    return this.get();
  }

  public final boolean exists(@NotNull Predicate<@Nullable T> predicate) {
    return !this.isEmpty() && predicate.test(this.get());
  }

  public final boolean forall(@NotNull Predicate<@Nullable T> predicate) {
    return this.isEmpty() || predicate.test(this.get());
  }

  @NotNull
  public final <U> Option<Tuple<@Nullable T, @Nullable U>> zip(@Nullable U right) {
    return this.isEmpty() ? none() : some(new Tuple<>(this.get(), right));
  }

  static final class None<T> extends Option<T> {

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

  static final class Some<T> extends Option<T> {

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
      return IteratorUtils.iterate(this.value);
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
      return obj instanceof Option.Some<?> && Objects.equals(this.value, ((Some<?>) obj).value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value);
    }
  }
}
