package pebble.data;

import java.util.Objects;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pebble.data.Either.Left;
import pebble.data.Either.Right;

public sealed interface Either<L, R> permits Left, Right {

  boolean isLeft();

  @Nullable
  L getLeft();

  @Nullable
  R getRight();

  @NotNull
  static <L, R> Either<@Nullable L, @Nullable R> rightOf(@Nullable R value) {
    return new Right<>(value);
  }

  @NotNull
  static <L, R> Either<@Nullable L, @Nullable R> leftOf(@Nullable L value) {
    return new Left<>(value);
  }

  default boolean isRight() {
    return !isLeft();
  }

  @NotNull
  default <U> Either<@Nullable L, @Nullable U> map(
      @NotNull Function<? super @Nullable R, ? extends @Nullable U> mapper) {
    Objects.requireNonNull(mapper);

    return this.isLeft() ? leftOf(this.getLeft()) : rightOf(mapper.apply(this.getRight()));
  }

  @NotNull
  @SuppressWarnings("unchecked")
  default <U> Either<@Nullable L, @Nullable U> flatMap(
      @NotNull
          Function<
                  ? super @Nullable R,
                  ? extends @NotNull Either<@Nullable L, ? extends @Nullable U>>
              mapper) {
    Objects.requireNonNull(mapper);

    return this.isLeft()
        ? (Either<L, U>) Objects.requireNonNull(this.getLeft())
        : (Either<L, U>) mapper.apply(this.getRight());
  }

  @NotNull
  default Option<@Nullable R> option() {
    return isLeft() ? Option.none() : Option.some(getRight());
  }

  final class Left<L, R> implements Either<L, R> {

    @Nullable private final L value;

    private Left(@Nullable L value) {
      this.value = value;
    }

    @Override
    public boolean isLeft() {
      return true;
    }

    @Nullable
    @Override
    public L getLeft() {
      return this.value;
    }

    @Override
    public R getRight() {
      throw new UnsupportedOperationException();
    }
  }

  final class Right<L, R> implements Either<L, R> {

    @Nullable private final R value;

    private Right(@Nullable R value) {
      this.value = value;
    }

    @Override
    public boolean isLeft() {
      return false;
    }

    @Override
    public L getLeft() {
      throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public R getRight() {
      return this.value;
    }
  }
}
