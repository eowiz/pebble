package pebble.converter;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.TypedArgumentConverter;
import pebble.data.Option;

public class OptionConverter extends TypedArgumentConverter<String, Option> {

  private static final Pattern SOME_PATTERN = Pattern.compile("Some\\((?<value>.+)\\)");

  protected OptionConverter() {
    super(String.class, Option.class);
  }

  @Override
  protected Option<?> convert(@NotNull String source) throws ArgumentConversionException {
    if (source.equals("None")) {
      return Option.none();
    }

    final var matcher = SOME_PATTERN.matcher(source);
    if (!matcher.matches()) {
      throw new ArgumentConversionException(
          "Cannot convert to " + Option.class.getName() + ": " + source);
    }

    final var value = matcher.group("value");

    return Option.some(value);
  }
}
