package pebble.io;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class Filez {

  private Filez() {}

  public static Stream<Path> listPaths(String first, String... more) throws NotDirectoryException {
    final var file = Path.of(first, more).toFile();
    final var files = file.listFiles();

    if (files == null) {
      throw new NotDirectoryException(file.toString());
    }

    return Arrays.stream(files).map(File::toPath);
  }
}
