package pebble.util;

import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utilities on {@link String} that are annotated with {@link Nullable} and {@link NotNull}.
 *
 * @author eowiz
 * @since 1.0.0
 */
public final class Stringz {

  private Stringz() {}

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
      @Nullable String string, @NotNull Supplier<@Nullable String> defaultSupplier) {
    Objects.requireNonNull(defaultSupplier);

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
      @Nullable String string, @NotNull Supplier<@Nullable String> defaultSupplier) {
    Objects.requireNonNull(defaultSupplier);

    if (string == null) {
      return defaultSupplier.get();
    }

    return string;
  }
}
