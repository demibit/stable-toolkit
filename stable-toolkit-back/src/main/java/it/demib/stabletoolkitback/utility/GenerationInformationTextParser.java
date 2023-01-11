package it.demib.stabletoolkitback.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GenerationInformationTextParser implements
    TextParser<Map<String, String>, List<String>> {

  @Override
  public Map<String, String> parse(List<String> textData) {
    Map<String, String> info = new HashMap<>();

    String[] rawInfo = textData.get(2).split(",");

    for (String parameters : rawInfo) {
      String[] splitParameters = parameters.split(":");
      info.put(splitParameters[0].trim(), splitParameters[1].trim());
    }

    info.put("Positive prompt", textData.get(0));
    info.put("Negative prompt", textData.get(1));

    return info;
  }
}
