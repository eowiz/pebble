package pebble.data;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ExceptionalFunction<T, R> {

  R apply(T t) throws Exception;

  static <T, R> Function<T, Either<Exception, R>> Try(
      @NotNull ExceptionalFunction<? super @Nullable T, ? extends @Nullable R> onTry) {
    return t -> {
      try {
        return Either.rightOf(onTry.apply(t));
      } catch (Exception e) {
        return Either.leftOf(e);
      }
    };
  }

  @NotNull
  static <T, R> Function<T, R> Try(
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
  static <T, R> Function<T, R> Try(
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
            if (e.getClass().isAssignableFrom(onCatch.getException())) {
              return onCatch.getBiFunction().apply(e, t);
            }
          }

          return defaultConsumer.apply(t);
        });
  }
}
