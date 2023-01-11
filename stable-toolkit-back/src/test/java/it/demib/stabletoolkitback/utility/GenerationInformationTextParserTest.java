package it.demib.stabletoolkitback.utility;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GenerationInformationTextParserTest {

  @InjectMocks
  private GenerationInformationTextParser generationInformationTextParser;


  @Nested
  @DisplayName("parse tests")
  class ParseTest {

    private final List<String> validTextData = List.of(
        "drawing of a (cat:1.2) wearing a chef hat with a (smug expression) floating in (miso soup:1.6) intricate (high detail)",
        "Negative prompt: lowres, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, jpeg artifacts, signature, watermark, out of frame, extra fingers, mutated hands, (poorly drawn hands), (poorly drawn face), (mutation), (deformed breasts), (ugly), blurry, (bad anatomy), (bad proportions), (extra limbs), cloned face",
        "Steps: 200, Sampler: DPM2 a, CFG scale: 10, Seed: 647308412, Face restoration: GFPGAN, Size: 640x640, Model hash: 44b5410d, Batch size: 8, Batch pos: 4, Denoising strength: 0.85, Eta: 0.8, Clip skip: 2, Mask blur: 4");


    @Test
    @DisplayName("Given a list of generation parameters, return a map of the parameters")
    public void test0() {
      // Given a list of generation parameters
      Map<String, String> manuallyParsedMap = new HashMap<>();

      manuallyParsedMap.put("Positive prompt",
          "drawing of a (cat:1.2) wearing a chef hat with a (smug expression) floating in (miso soup:1.6) intricate (high detail)");
      manuallyParsedMap.put("Negative prompt",
          "Negative prompt: lowres, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality");
      manuallyParsedMap.put("Steps", "200");
      manuallyParsedMap.put("Sampler", "DPM2 a");
      manuallyParsedMap.put("CFG scale", "10");
      manuallyParsedMap.put("Seed", "647308412");
      manuallyParsedMap.put("Face restoration", "GFPGAN");
      manuallyParsedMap.put("Size", "640x640");
      manuallyParsedMap.put("Model hash", "44b5410d");
      manuallyParsedMap.put("Batch size", "8");
      manuallyParsedMap.put("Batch pos", "4");
      manuallyParsedMap.put("Denoising strength", "0.85");
      manuallyParsedMap.put("Eta", "0.8");
      manuallyParsedMap.put("Clip skip", "2");
      manuallyParsedMap.put("Mask blur", "4");

      // Then return a map of the parameters
      assertThat(generationInformationTextParser.parse(validTextData))
          .containsAllEntriesOf(manuallyParsedMap);
    }
  }
}
