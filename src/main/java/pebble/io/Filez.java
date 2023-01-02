package pebble.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Filez {

  private Filez() {}

  public static Stream<Path> listPaths(Path path) throws NotDirectoryException {
    final var file = path.toFile();
    final var files = file.listFiles();

    if (files == null) {
      throw new NotDirectoryException(file.toString());
    }

    return Arrays.stream(files).map(File::toPath);
  }

  public static Stream<Path> listPaths(String first, String... more) throws NotDirectoryException {
    return listPaths(Path.of(first, more));
  }

  public static Stream<Path> listGlobs(Path root, String... globs) throws IOException {
    final var fileSystem = FileSystems.getDefault();

    final var pathMatchers = Arrays.stream(globs)
        .map(it -> "glob:" + it)
        .map(fileSystem::getPathMatcher)
        .toList();

    final var visitor = new GlobFileVisitor(pathMatchers);
    Files.walkFileTree(root, visitor);

    return visitor.matchedPathsBuilder.build();
  }

  static class GlobFileVisitor implements FileVisitor<Path> {

    final List<PathMatcher> pathMatchers;

    final Stream.Builder<Path> matchedPathsBuilder;

    GlobFileVisitor(List<PathMatcher> pathMatchers) {
      this.pathMatchers = pathMatchers;
      this.matchedPathsBuilder = Stream.builder();
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
        throws IOException {
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      final var isMatch = pathMatchers.stream()
          .anyMatch(matcher -> matcher.matches(file));

      if (isMatch) {
        this.matchedPathsBuilder.add(file);
      }

      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
      return FileVisitResult.CONTINUE;
    }
  }
}
