package it.demib.stabletoolkitback.utility;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

@ExtendWith(MockitoExtension.class)
public class TagParserTest {

  @InjectMocks
  private TagParser tagParser;

  @Nested
  @DisplayName("parse tests")
  class ParseTest {

    @Test
    @DisplayName("Given a list of tags, and a string to parse, return list of strings that appear in both")
    public void test0() {
      // Given a list of tags, and a string to parse
      List<String> tags = List.of("cats", "dogs", "in the park", "cute");
      String toParse = "a group of (cute dogs) playing fetch in the park";
      // Then
      assertThat(tagParser.parse(Pair.of(tags, toParse))).containsOnly("dogs", "in the park",
          "cute");
    }
  }

}
