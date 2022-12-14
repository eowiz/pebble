package pebble.data;

import java.util.Objects;
import java.util.function.BiFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @param <T>
 * @see <a
 *     href="https://qiita.com/q-ikawa/items/3f55089e9081e1a854bc">Java8のStreamやOptionalでクールに例外を処理する方法
 *     - Qiita</a>
 */
public class ExceptionCatch<T, R> {

  private final Class<? extends @NotNull Exception> exception;

  private final BiFunction<@NotNull Exception, @Nullable T, @Nullable R> biFunction;

  public ExceptionCatch(
      @NotNull Class<? extends @NotNull Exception> exception,
      @NotNull BiFunction<@NotNull Exception, @Nullable T, @Nullable R> biFunction) {
    Objects.requireNonNull(exception);
    Objects.requireNonNull(biFunction);

    this.exception = exception;
    this.biFunction = biFunction;
  }

  @NotNull
  public Class<? extends Exception> getException() {
    return this.exception;
  }

  @NotNull
  public BiFunction<Exception, T, R> getBiFunction() {
    return this.biFunction;
  }

  @NotNull
  public static <T, R> ExceptionCatch<T, R> of(
      @NotNull Class<? extends Exception> exception,
      @NotNull BiFunction<Exception, T, R> biFunction) {
    return new ExceptionCatch<>(exception, biFunction);
  }
}
