package pebble.util;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pebble.data.Either;
import pebble.data.ExceptionCatch;
import pebble.function.ExceptionalConsumer;
import pebble.function.ExceptionalFunction;

@SuppressWarnings("java:S100")
public final class ExceptionOps {

  private ExceptionOps() {}

  @NotNull
  public static <T, R> Function<@Nullable T, @NotNull Either<@NotNull Exception, @Nullable R>> TryF(
      @NotNull ExceptionalFunction<? super @Nullable T, ? extends @Nullable R> onTry) {
    Objects.requireNonNull(onTry);

    return t -> {
      try {
        return Either.rightOf(onTry.apply(t));
      } catch (Exception e) {
        return Either.leftOf(e);
      }
    };
  }

  @NotNull
  public static <T, R> Function<@Nullable T, @Nullable R> TryF(
      @NotNull ExceptionalFunction<? super @Nullable T, ? extends @Nullable R> onTry,
      @NotNull BiFunction<? super @NotNull Exception, ? super @Nullable T, ? extends @Nullable R> onCatch) {
    Objects.requireNonNull(onTry);
    Objects.requireNonNull(onCatch);

    return t -> {
      try {
        return onTry.apply(t);
      } catch (Exception e) {
        return onCatch.apply(e, t);
      }
    };
  }

  @SafeVarargs
  @NotNull
  static <T, R> Function<@Nullable T, @Nullable R> TryF(
      @NotNull ExceptionalFunction<? super @Nullable T, ? extends @Nullable R> onTry,
      @NotNull Function<@Nullable T, @Nullable R> defaultConsumer,
      @NotNull ExceptionCatch<? super @Nullable T, ? extends @Nullable R>... onCatches) {
    Objects.requireNonNull(onTry);
    Objects.requireNonNull(defaultConsumer);
    Objects.requireNonNull(onCatches);

    return TryF(
        onTry,
        (e, t) -> {
          for (var onCatch : onCatches) {
            if (e.getClass().isAssignableFrom(onCatch.exception())) {
              return onCatch.biFunction().apply(e, t);
            }
          }

          return defaultConsumer.apply(t);
        });
  }

  @SuppressWarnings("java:S108")
  @NotNull
  static <T> Consumer<@Nullable T> TryC(@NotNull ExceptionalConsumer<@Nullable T> onTry) {
    Objects.requireNonNull(onTry);

    return t -> {
      try {
        onTry.accept(t);
      } catch (Exception ignore) {
      }
    };
  }

  @NotNull
  static <T> Consumer<@Nullable T> TryC(
      @NotNull ExceptionalConsumer<@Nullable T> onTry,
      @NotNull BiConsumer<? super @NotNull Exception, ? super @Nullable T> onCatch) {
    Objects.requireNonNull(onTry);
    Objects.requireNonNull(onCatch);

    return t -> {
      try {
        onTry.accept(t);
      } catch (Exception e) {
        onCatch.accept(e, t);
      }
    };
  }
}
