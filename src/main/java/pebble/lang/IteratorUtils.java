package pebble.lang;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class IteratorUtils {

  private IteratorUtils() {}

  @NotNull
  public static <T> Iterator<@Nullable T> empty() {
    return Collections.emptyIterator();
  }

  @NotNull
  public static <T> Iterator<@Nullable T> singleton(@Nullable T value) {
    return new Iterator<>() {

      private boolean hasNext = true;

      @Override
      public boolean hasNext() {
        return this.hasNext;
      }

      @Override
      public T next() {
        if (!this.hasNext) {
          throw new NoSuchElementException();
        }

        this.hasNext = false;

        return null;
      }
    };
  }

  @NotNull
  @SafeVarargs
  public static <T> Iterator<@Nullable T> iterate(@Nullable T... values) {
    if (values == null) {
      return singleton(null);
    }

    return Arrays.asList(values).iterator();
  }
}
