package pebble.lang;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jetbrains.annotations.NotNull;

public final class Streams {

  private Streams() {}

  @NotNull
  public static <T> Stream<T> from(@NotNull Iterator<T> iterator) {
    Objects.requireNonNull(iterator);

    final var spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);

    return StreamSupport.stream(spliterator, false);
  }
}