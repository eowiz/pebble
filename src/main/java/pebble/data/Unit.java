package pebble.data;

import java.util.Iterator;
import org.jetbrains.annotations.NotNull;
import pebble.lang.Iterators;

public record Unit() implements Iterable<Void> {

  @NotNull
  @Override
  public Iterator<Void> iterator() {
    return Iterators.empty();
  }
}
