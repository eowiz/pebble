package pebble.util;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pebble.data.Either;
import pebble.data.ExceptionCatch;
import pebble.function.ExceptionalFunction;

public final class ExceptionOps {

  private ExceptionOps() {}

  @NotNull
  public static <T, R> Function<@Nullable T, @NotNull Either<@NotNull Exception, @Nullable R>> Try(
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
  public static <T, R> Function<@Nullable T, @Nullable R> Try(
      @NotNull ExceptionalFunction<? super @Nullable T, ? extends @Nullable R> onTry,
      @NotNull BiFunction<@NotNull Exception, @Nullable T, @Nullable R> onCatch) {
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

  @NotNull
  static <T, R> Function<@Nullable T, @Nullable R> Try(
      @NotNull ExceptionalFunction<? super @Nullable T, ? extends @Nullable R> onTry,
      @NotNull Function<@Nullable T, @Nullable R> defaultConsumer,
      @NotNull ExceptionCatch<@Nullable T, @Nullable R>... onCatches) {
    Objects.requireNonNull(onTry);
    Objects.requireNonNull(defaultConsumer);
    Objects.requireNonNull(onCatches);

    return Try(
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
}
