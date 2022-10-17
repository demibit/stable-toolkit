package it.demib.stabletoolkitback.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.demib.stabletoolkitback.model.entity.Image;
import it.demib.stabletoolkitback.model.entity.TagHolder;
import it.demib.stabletoolkitback.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

  @InjectMocks
  private TagService tagService;

  @Mock
  private TagRepository tagRepository;

  @Mock
  private ImageService imageService;

  @Nested
  @DisplayName("findAll tests")
  class FindAllTest {

    @Test
    @DisplayName("Given method is called, verify tags are queried from db, and the list of tags are returned")
    public void test0() {
      // Given method is called
      List<String> mockTags = List.of("dogs", "cats");

      when(tagRepository.findAll()).thenReturn(List.of(TagHolder.builder().tags(mockTags).build()));
      List<String> tags = tagService.findAll();
      // Then verify tags are queried from db, and parsed into a list of strings
      assertThat(tags).isEqualTo(mockTags);
      verify(tagRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Given method is called, but tagRepository returns null, verify a new TagHolder is saved, and returned")
    public void test1() {
      // Given method is called, but tagRepository returns null

      when(tagRepository.findAll()).thenReturn(List.of(TagHolder.builder().build()));
      when(tagRepository.save(any())).then(
          AdditionalAnswers.returnsFirstArg()); // Return same object as input
      List<String> tags = tagService.findAll();
      // Then verify a new TagHolder is saved, and returned
      assertThat(tags.size()).isEqualTo(0);
      verify(tagRepository, times(1)).save(any());
    }
  }

  @Nested
  @DisplayName("addTags tests")
  class AddTagsTest {

    @Test
    @DisplayName("Given a list of tags and an empty db, add them to db")
    public void test0() {
      // Given a list of tags and an empty db
      List<String> mockTags = List.of("dogs", "cats");
      when(tagRepository.findAll()).thenReturn(
          List.of(TagHolder.builder().tags(new ArrayList<>()).build()));
      when(tagRepository.save(any())).then(
          AdditionalAnswers.returnsFirstArg()); // Return same object as input

      List<String> newTags = tagService.addTags(mockTags);
      // Then add them to db
      assertThat(newTags).isEqualTo(mockTags);
    }

    @Test
    @DisplayName("Given a list of tags and a db with a value, add only values that weren't in db")
    public void test1() {
      // Given a list of tags and a db with a value
      List<String> mockTags = List.of("dogs", "cats");
      when(tagRepository.findAll()).thenReturn(
          List.of(TagHolder.builder().tags(new ArrayList<>(List.of("dogs"))).build()));
      when(tagRepository.save(any())).then(
          AdditionalAnswers.returnsFirstArg()); // Return same object as input

      List<String> newTags = tagService.addTags(mockTags);
      // Then add only values that weren't in db
      assertThat(newTags).isEqualTo(mockTags);
    }
  }

  @Nested
  @DisplayName("deleteTags tests")
  class DeleteTagsTest {

    @Test
    @DisplayName("Given a list of tags, and a db with no tags in it, return an empty list")
    public void test0() {
      // Given a list of tags, and a db with no tags in it
      List<String> mockTags = List.of("dogs", "cats");
      when(tagRepository.findAll()).thenReturn(
          List.of(TagHolder.builder().tags(new ArrayList<>()).build()));
      when(tagRepository.save(any())).then(
          AdditionalAnswers.returnsFirstArg()); // Return same object as input

      List<String> newTags = tagService.deleteTags(mockTags);
      // Then return an empty list
      assertThat(newTags.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Given a list of tags a db with 1 tag from it in it, return a list without that tag")
    public void test1() {
      // Given a list of tags a db with 1 tag from it in it
      List<String> mockTags = List.of("dogs", "cats");
      when(tagRepository.findAll()).thenReturn(
          List.of(TagHolder.builder().tags(new ArrayList<>(List.of("dogs", "fish"))).build()));
      when(tagRepository.save(any())).then(
          AdditionalAnswers.returnsFirstArg()); // Return same object as input

      List<String> newTags = tagService.deleteTags(mockTags);
      // Then return a list without that tag
      assertThat(newTags.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Given an empty list, verify deleteAll is called and an empty list is returned")
    public void test2() {
      // Given an empty list
      List<String> mockTags = new ArrayList<>();

      List<String> newTags = tagService.deleteTags(mockTags);
      // Then verify deleteAll is called and an empty list is returned
      assertThat(newTags.size()).isEqualTo(0);
      verify(tagRepository, times(1)).deleteAll();
    }
  }


}
