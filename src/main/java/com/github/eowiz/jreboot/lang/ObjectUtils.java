package com.github.eowiz.jreboot.lang;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ObjectUtils {

  private ObjectUtils() {}

  public static boolean allNull(@Nullable Object... objects) {
    if (objects == null) {
      return true;
    }

    for (var object : objects) {
      if (object != null) {
        return false;
      }
    }

    return true;
  }

  public static boolean allNull(@Nullable Supplier<?>... suppliers) {
    if (suppliers == null) {
      return false;
    }

    for (var supplier : suppliers) {
      if (supplier.get() != null) {
        return false;
      }
    }

    return true;
  }

  public static boolean anyNull(@Nullable Object... objects) {
    if (objects == null) {
      return true;
    }

    for (var object : objects) {
      if (object == null) {
        return true;
      }
    }

    return false;
  }

  public static boolean anyNull(@Nullable Supplier<?>... suppliers) {
    if (suppliers == null) {
      return true;
    }

    for (var supplier : suppliers) {
      if (supplier.get() == null) {
        return true;
      }
    }

    return false;
  }

  public static boolean allNotNull(@Nullable Object... objects) {
    return !anyNull(objects);
  }

  /**
   * Checks if all values supplied in the array are not {@code nulls}.
   *
   * @param suppliers the suppliers to test, if the array contains {@code null} then this method throws {@link NullPointerException}
   * @return {@code false} if there is at least one {@code null} value in values supplied from the array or the array is {@code null},
   *    {@code true} if all values supplied from the array are not {@code nulls} or array contains no elements.
   */
  public static boolean allNotNull(@Nullable Supplier<?>... suppliers) {
    return !anyNull(suppliers);
  }

  @Nullable
  public static <T> Function<T, T> CONST(@Nullable T t) {
    return ignore -> t;
  }

  @Nullable
  public static <T> T defaultIfNull(@Nullable T object, @Nullable T defaultValue) {
    if (object != null) {
      return object;
    }

    return defaultValue;
  }

  @Nullable
  public static <T> T defaultIfNull(@Nullable T object, @Nonnull Supplier<? extends T> defaultSupplier) {
    if (object != null) {
      return object;
    }

    return defaultSupplier.get();
  }

  /**
   * Returns the result of calling {@code toString} for a non- {@code null} argument and {@code null} for a {@code null} argument.
   *
   * @param object an object
   * @return the result of calling {@code toString} for a non- {@code null} argument and {@code null} for a {@code null} argument
   */
  @Nullable
  public static String toString(@Nullable Object object) {
    return Objects.toString(object, null);
  }
}
