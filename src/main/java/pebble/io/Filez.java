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
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public final class Filez {

  private Filez() {}

  @NotNull
  public static Stream<@NotNull Path> listPaths(@NotNull Path path) throws NotDirectoryException {
    Objects.requireNonNull(path);

    final var file = path.toFile();
    final var files = file.listFiles();

    if (files == null) {
      throw new NotDirectoryException(file.toString());
    }

    return Arrays.stream(files).map(File::toPath);
  }

  @NotNull
  public static Stream<@NotNull Path> listPaths(@NotNull String first, @NotNull String... more) throws NotDirectoryException {
    Objects.requireNonNull(first);
    Objects.requireNonNull(more);

    return listPaths(Path.of(first, more));
  }

  @NotNull
  public static Stream<@NotNull Path> listGlobs(@NotNull Path root, @NotNull String... globs) throws IOException {
    Objects.requireNonNull(root);
    Objects.requireNonNull(globs);

    final var fileSystem = FileSystems.getDefault();

    final var pathMatchers =
        Arrays.stream(globs).map(it -> "glob:" + it).map(fileSystem::getPathMatcher).toList();

    final var visitor = new GlobFileVisitor(pathMatchers);
    Files.walkFileTree(root, visitor);

    return visitor.matchedPathsBuilder.build();
  }

  static class GlobFileVisitor implements FileVisitor<Path> {

    @NotNull
    final List<@NotNull PathMatcher> pathMatchers;

    @NotNull
    final Stream.Builder<@NotNull Path> matchedPathsBuilder;

    GlobFileVisitor(@NotNull List<@NotNull PathMatcher> pathMatchers) {
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
      final var normalized = file.normalize();
      final var isMatch = pathMatchers.stream().anyMatch(matcher -> matcher.matches(normalized));

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
