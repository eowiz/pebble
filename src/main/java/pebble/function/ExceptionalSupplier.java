package pebble.function;

import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ExceptionalSupplier<T> {

  @SuppressWarnings("java:S112")
  @Nullable
  T get() throws Exception;
}
