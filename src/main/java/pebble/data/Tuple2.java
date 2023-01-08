package pebble.data;

import java.util.Objects;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("java:S117")
public record Tuple2<T, U>(@Nullable T _1, @Nullable U _2) {

  @NotNull
  public static <T, U> Tuple2<@Nullable T, @Nullable U> of(@Nullable T _1, @Nullable U _2) {
    return new Tuple2<>(_1, _2);
  }

  @NotNull
  public Tuple2<@Nullable U, @Nullable T> swap() {
    return new Tuple2<>(this._2, this._1);
  }

  @NotNull
  public <V, W>
      Tuple2<@NotNull Tuple2<@Nullable T, @Nullable V>, @NotNull Tuple2<@Nullable U, @Nullable W>>
          zip(@NotNull Tuple2<@Nullable V, @Nullable W> that) {
    Objects.requireNonNull(that);

    return new Tuple2<>(new Tuple2<>(this._1, that._1), new Tuple2<>(this._2, that._2));
  }

  @NotNull
  public <V> Tuple2<@Nullable T, @Nullable V> map(
      @NotNull Function<? super @Nullable U, ? extends @Nullable V> mapping) {
    Objects.requireNonNull(mapping);

    return new Tuple2<>(this._1, mapping.apply(this._2));
  }

  @NotNull
  public <V> Tuple2<@Nullable T, @Nullable V> rightMap(
      @NotNull Function<? super @Nullable U, ? extends @Nullable V> mapping) {
    Objects.requireNonNull(mapping);

    return this.map(mapping);
  }

  @NotNull
  public <V> Tuple2<@Nullable V, @Nullable U> leftMap(
      @NotNull Function<? super @Nullable T, ? extends @Nullable V> mapping) {
    Objects.requireNonNull(mapping);

    return new Tuple2<>(mapping.apply(this._1), this._2);
  }
}
