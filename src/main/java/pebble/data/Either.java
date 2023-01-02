package pebble.data;

import java.util.Objects;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Either<L, R> {

  public abstract boolean isLeft();

  @Nullable
  public abstract L getLeft();

  @Nullable
  public abstract R getRight();

  @NotNull
  public static <L, R> Either<@Nullable L, @Nullable R> rightOf(@Nullable R value) {
    return new Right<>(value);
  }

  @NotNull
  public static <L, R> Either<@Nullable L, @Nullable R> leftOf(@Nullable L value) {
    return new Left<>(value);
  }

  public final boolean isRight() {
    return !isLeft();
  }

  @NotNull
  public final <U> Either<@Nullable L, @Nullable U> map(
      @NotNull Function<? super R, ? extends U> mapper) {
    Objects.requireNonNull(mapper);

    return this.isLeft() ? leftOf(this.getLeft()) : rightOf(mapper.apply(this.getRight()));
  }

  @NotNull
  @SuppressWarnings("unchecked")
  public final <U> Either<@Nullable L, @Nullable U> flatMap(
      @NotNull Function<? super R, ? extends Either<L, ? extends U>> mapper) {
    Objects.requireNonNull(mapper);

    return this.isLeft()
        ? (Either<L, U>) Objects.requireNonNull(this.getLeft())
        : (Either<L, U>) mapper.apply(this.getRight());
  }

  @NotNull
  public Option<@Nullable R> toOption() {
    return isLeft() ? Option.none() : Option.some(getRight());
  }

  private Either() {}

  private static class Left<L, R> extends Either<L, R> {

    @Nullable private final L value;

    private Left(@Nullable L value) {
      this.value = value;
    }

    @Override
    public boolean isLeft() {
      return true;
    }

    @Override
    public L getLeft() {
      return this.value;
    }

    @Override
    public R getRight() {
      throw new UnsupportedOperationException();
    }
  }

  private static class Right<L, R> extends Either<L, R> {

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

    @Override
    public R getRight() {
      return this.value;
    }
  }
}
