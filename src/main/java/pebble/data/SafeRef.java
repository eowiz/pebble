package pebble.data;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pebble.data.SafeRef.NoRef;
import pebble.data.SafeRef.Ref;

/**
 * A class provides a safe way to call an object's methods. This is similar to {@link Optional}.
 *
 * <p>{@link Optional} has methods, {@link Optional#map(Function)} and {@link
 * Optional#flatMap(Function)}, which names derived from Functor and Monad, but their behaviors
 * satisfy neither the Functor low nor the Monad low. The main purpose of this class is providing
 * {@link #safe(Function)} and {@link #flatSafe(Function)} instead of {@link Optional#map(Function)}
 * and {@link Optional#flatMap(Function)}.
 *
 * <pre>{@code
 * SareRef.ofNullable(x)
 *   .safe(X::getY)
 *   .safe(Y::getZ)
 *   .orElse();
 * }</pre>
 *
 * @see Optional
 * @see Option
 * @see <a href="http://blog.vavr.io/the-agonizing-death-of-an-astronaut/">The agonizing death of an
 *     astronaut - Vavr</a>
 * @param <T> the type of value
 */
public sealed interface SafeRef<T> permits Ref, NoRef {

  /**
   * If a value is present, returns the value, otherwise throws {@code NoSuchElementException}.
   *
   * @return non-null value
   * @throws NoSuchElementException if no value is present
   */
  @NotNull
  T get();

  @NotNull
  static <T> SafeRef<@Nullable T> ref(@NotNull T value) {
    Objects.requireNonNull(value);

    return new Ref<>(value);
  }

  @SuppressWarnings("unchecked")
  static <T> SafeRef<@Nullable T> noRef() {
    return (SafeRef<T>) NoRef.NO_REF;
  }

  boolean isRef();

  default boolean isNoRef() {
    return !this.isRef();
  }

  @NotNull
  static <T> SafeRef<@Nullable T> ofNullable(@Nullable T value) {
    return value == null ? noRef() : ref(value);
  }

  @NotNull
  static <T> SafeRef<@Nullable T> of(@NotNull T value) {
    Objects.requireNonNull(value);

    return ref(value);
  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  @NotNull
  static <T> SafeRef<@Nullable T> from(@NotNull Optional<@Nullable T> optional) {
    Objects.requireNonNull(optional);

    return optional.map(SafeRef::of).orElse(noRef());
  }

  @NotNull
  static <T> SafeRef<@Nullable T> from(@NotNull Option<@Nullable T> option) {
    Objects.requireNonNull(option);

    return option.map(SafeRef::ofNullable).orElse(noRef());
  }

  @NotNull
  default <U> SafeRef<@Nullable U> safe(
      @NotNull Function<? super @NotNull T, ? extends @Nullable U> accessor) {
    Objects.requireNonNull(accessor);

    return this.isRef() ? ofNullable(accessor.apply(this.get())) : noRef();
  }

  @NotNull
  @SuppressWarnings("unchecked")
  default <U> SafeRef<@Nullable U> flatSafe(
      @NotNull Function<? super @NotNull T, SafeRef<? extends U>> accessor) {
    Objects.requireNonNull(accessor);

    return this.isRef() ? (SafeRef<U>) accessor.apply(this.get()) : noRef();
  }

  @NotNull
  default SafeRef<T> filter(@NotNull Predicate<@NotNull T> predicate) {
    if (this.isNoRef()) {
      return this;
    }

    return predicate.test(this.get()) ? this : noRef();
  }

  @Nullable
  default T orNull() {
    return this.isRef() ? this.get() : null;
  }

  @NotNull
  default Optional<@NotNull T> toOptional() {
    return this.isRef() ? Optional.of(this.get()) : Optional.empty();
  }

  @NotNull
  default Option<@Nullable T> toOption() {
    return this.isRef() ? Option.of(this.get()) : Option.none();
  }

  final class Ref<T> implements SafeRef<T> {

    @NotNull private final T value;

    public Ref(@NotNull T value) {
      this.value = value;
    }

    @NotNull
    @Override
    public T get() {
      return this.value;
    }

    @Override
    public boolean isRef() {
      return true;
    }
  }

  final class NoRef<T> implements SafeRef<T> {

    private static final SafeRef<Void> NO_REF = new NoRef<>();

    private NoRef() {}

    @NotNull
    @Override
    public T get() {
      throw new NoSuchElementException();
    }

    @Override
    public boolean isRef() {
      return false;
    }
  }
}
