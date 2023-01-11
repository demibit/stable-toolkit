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

    String[] rawInfo = (textData.get(2) + ",").split(" ");

    for (int i = 0; i < rawInfo.length; i++) {
      if (rawInfo[i].contains(",")) {
        StringBuilder genTag = new StringBuilder();
        StringBuilder genVal = new StringBuilder(
            rawInfo[i].replaceAll(":", "").replaceAll(",", "") + " ");

        boolean atTag = false;

        for (int j = i - 1; j >= 0 && !rawInfo[j].contains(","); j--) {
          if (rawInfo[j].contains(":") || atTag) {
            atTag = true;
            genTag.insert(0,
                rawInfo[j].replaceAll(":", "").replaceAll(",", "") + " ");
          } else {
            genVal.insert(0,
                rawInfo[j].replaceAll(":", "").replaceAll(",", "") + " ");
          }
        }
        info.put(genTag.toString().trim(), genVal.toString().trim());
      }
    }

    info.put("Positive prompt", textData.get(0));
    info.put("Negative prompt", textData.get(1));

    return info;
  }
}
