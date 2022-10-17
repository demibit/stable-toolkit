package it.demib.stabletoolkitback.controller;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.demib.stabletoolkitback.service.TagService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TagControllerTest {

  @InjectMocks
  private TagController tagController;

  @Mock
  private TagService tagService;

  @Nested
  @DisplayName("getTags tests")
  class GetTagsTest {

    @Test
    @DisplayName("Given method is called, verify findAll is called")
    public void test0() {
      // Given method is called
      when(tagService.findAll()).thenReturn(List.of("test"));
      tagController.getTags();
      // Then verify findAll is called
      verify(tagService, times(1)).findAll();
    }
  }

  @Nested
  @DisplayName("addTags tests")
  class AddTagsTest {

    @Test
    @DisplayName("Given method is called, verify addTags is called")
    public void test0() {
      // Given method is called
      when(tagService.addTags(anyList())).thenReturn(List.of("test"));
      tagController.addTags(new ArrayList<>());
      // Then verify findAll is called
      verify(tagService, times(1)).addTags(anyList());
    }
  }

  @Nested
  @DisplayName("deleteTags tests")
  class DeleteTagsTest {

    @Test
    @DisplayName("Given method is called, verify deleteTags is called")
    public void test0() {
      // Given method is called
      when(tagService.deleteTags(anyList())).thenReturn(List.of("test"));
      tagController.deleteTags(new ArrayList<>());
      // Then verify findAll is called
      verify(tagService, times(1)).deleteTags(anyList());
    }
  }
}
