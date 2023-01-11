package it.demib.stabletoolkitback.utility;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class TagParser implements TextParser<List<String>, Pair<List<String>, String>> {

  @Override
  public List<String> parse(Pair<List<String>, String> tags) {
    List<String> currentTags = tags.getFirst();
    String taggable = tags.getSecond();

    return currentTags.stream().filter(s -> isContain(taggable, s))
        .collect(Collectors.toList());
  }

  public static boolean isContain(String source, String subItem) {
    return Objects.nonNull(source) && Objects.nonNull(subItem) && Pattern.compile(
        "\\b" + subItem + "\\b").matcher(source).find();
  }
}
