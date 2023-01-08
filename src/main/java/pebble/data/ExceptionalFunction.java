package pebble.data;

import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ExceptionalFunction<T, R> {

  @Nullable
  R apply(@Nullable T t) throws Exception;
}
