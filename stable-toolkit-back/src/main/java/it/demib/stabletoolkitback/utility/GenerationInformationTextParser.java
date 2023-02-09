package it.demib.stabletoolkitback.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@NoArgsConstructor
public class GenerationInformationTextParser implements
    TextParser<Map<String, String>, List<String>> {

  @Override
  public Map<String, String> parse(final List<String> textData) {
    if (!Objects.equals(textData.size(), 3)) {
      log.warn(
          "GenerationInformationTextParser::parse - Incorrect number of parameters given, skipping generation text parsing");
      return new HashMap<>();
    }

    final Map<String, String> info = new HashMap<>();

    final Pattern generationParametersPattern = Pattern.compile("^(([^,]+):\\s([^,]+),?)*");
    final String generationParameters = textData.get(2);

    if (!generationParametersPattern.matcher(generationParameters).matches()) {
      log.warn(
          "GenerationInformationTextParser::parse - Unable to match expected pattern for generation parameters, skipping");
    } else {
      final String[] rawInfo = generationParameters.split(",");

      for (String parameters : rawInfo) {
        final String[] splitParameters = parameters.split(":");
        info.put(splitParameters[0].trim(), splitParameters[1].trim());
      }
    }

    info.put("Positive prompt", textData.get(0));
    info.put("Negative prompt", textData.get(1));

    return info;
  }
}
