package pebble.data;

import java.util.Iterator;
import org.jetbrains.annotations.NotNull;
import pebble.util.Iteratorz;

public record Tuple0() implements Iterable<Void> {

  @NotNull
  @Override
  public Iterator<Void> iterator() {
    return Iteratorz.empty();
  }
}
