package pebble.lang;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Streamz {

  private Streamz() {}

  @NotNull
  public static <T> Stream<@Nullable T> from(@NotNull Iterator<@Nullable T> iterator) {
    Objects.requireNonNull(iterator);

    final var spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);

    return StreamSupport.stream(spliterator, false);
  }
}
