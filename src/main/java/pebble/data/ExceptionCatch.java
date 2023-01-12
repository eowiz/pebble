package pebble.data;

import java.util.Objects;
import java.util.function.BiFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @param <T>
 * @see <a
 * href="https://qiita.com/q-ikawa/items/3f55089e9081e1a854bc">Java8のStreamやOptionalでクールに例外を処理する方法 -
 * Qiita</a>
 */
public record ExceptionCatch<T, R>(@NotNull Class<? extends @NotNull Exception> exception,
                                   @NotNull BiFunction<@NotNull Exception, @Nullable T, @Nullable R> biFunction) {

  public ExceptionCatch {
    Objects.requireNonNull(exception);
    Objects.requireNonNull(biFunction);
  }

  @NotNull
  public static <T, R> ExceptionCatch<@Nullable T, @Nullable R> of(
      @NotNull Class<? extends @NotNull Exception> exception,
      @NotNull BiFunction<@NotNull Exception, @Nullable T, @Nullable R> biFunction) {
    return new ExceptionCatch<>(exception, biFunction);
  }
}
