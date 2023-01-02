package pebble.data;

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

  static <T, R> Function<T, R> Try(
      @NotNull ExceptionalFunction<? super @Nullable T, ? extends @Nullable R> onTry,
      @NotNull BiFunction<Exception, @Nullable T, @Nullable R> onCatch) {
    return t -> {
      try {
        return onTry.apply(t);
      } catch (Exception e) {
        return onCatch.apply(e, t);
      }
    };
  }
}
