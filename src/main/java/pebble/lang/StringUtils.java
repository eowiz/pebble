package pebble.lang;

import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utilities on {@link String} that are annotated with {@link Nullable} and {@link NotNull}.
 *
 * @since 1.0.0
 * @author eowiz
 */
public final class StringUtils {

  private StringUtils() {}

  public static boolean isEmpty(@Nullable String string) {
    if (string == null) {
      return true;
    }

    return string.isEmpty();
  }

  public static boolean isBlank(@Nullable String string) {
    if (string == null) {
      return true;
    }

    return string.isBlank();
  }

  @Nullable
  public static String defaultIfBlank(@Nullable String string, @Nullable String defaultString) {
    if (isBlank(string)) {
      return defaultString;
    }

    return string;
  }

  @Nullable
  public static String defaultIfEmpty(
      @Nullable String string, @NotNull Supplier<String> defaultSupplier) {
    if (isEmpty(string)) {
      return defaultSupplier.get();
    }

    return string;
  }

  @NotNull
  public static String defaultString(@Nullable String string) {
    return defaultString(string, "");
  }

  @Nullable
  public static String defaultString(@Nullable String string, @Nullable String defaultString) {
    if (string == null) {
      return defaultString;
    }

    return string;
  }

  @Nullable
  public static String defaultString(
      @Nullable String string, @NotNull Supplier<String> defaultSupplier) {
    if (string == null) {
      return defaultSupplier.get();
    }

    return string;
  }
}
