package it.demib.stabletoolkitback.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import it.demib.stabletoolkitback.model.dto.ImageQueryParameters;
import it.demib.stabletoolkitback.model.entity.Image;
import it.demib.stabletoolkitback.repository.ImageRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

  @InjectMocks
  private ImageService imageService;

  @Mock
  private ImageRepository imageRepository;

  @Mock
  private MongoCollection<Document> mongoCollection;

  @Mock
  private TagService tagService;

  @Mock
  private FolderService folderService;

  @Nested
  @DisplayName("getImagesBy tests")
  class GetImagesByTest {

    @Test
    @DisplayName("Given a query with all fields, verify all fields have been used")
    public void test0() {
      // Given a query with all fields
      ImageQueryParameters imageQueryParameters = mock(ImageQueryParameters.class);
      when(imageQueryParameters.getLocation()).thenReturn(List.of("C:\\location"));
      when(imageQueryParameters.getAfterDate()).thenReturn(
          Instant.now().minus(10, ChronoUnit.SECONDS));
      when(imageQueryParameters.getBeforeDate()).thenReturn(
          Instant.now().plus(10, ChronoUnit.SECONDS));
      when(imageQueryParameters.getTags()).thenReturn(List.of("tag"));
      when(imageQueryParameters.getSteps()).thenReturn(List.of(20, 40));
      when(imageQueryParameters.getSampler()).thenReturn(List.of("DDIM", "Euler a"));
      when(imageQueryParameters.getDenoise()).thenReturn(List.of(0.6, 0.7));
      when(imageQueryParameters.getCfg()).thenReturn(List.of(6D, 12D));
      when(imageQueryParameters.getModelHash()).thenReturn(List.of("06c90210"));
      when(imageQueryParameters.getFaceRestoration()).thenReturn(List.of("Codeformer"));
      when(imageQueryParameters.getHypernet()).thenReturn(List.of("japanese_cartoon_2"));
      when(imageQueryParameters.getClipSkip()).thenReturn(List.of(1, 2));
      when(imageQueryParameters.getWidth()).thenReturn(List.of(512, 640));
      when(imageQueryParameters.getHeight()).thenReturn(List.of(512, 640));
      when(imageQueryParameters.getCount()).thenReturn(0);

      doReturn(mock(AggregateIterable.class)).when(mongoCollection).aggregate(any());

      imageService.getImagesBy(imageQueryParameters);
      // Then verify all fields have been used
      verify(imageQueryParameters, times(2)).getLocation();
      verify(imageQueryParameters, times(2)).getAfterDate();
      verify(imageQueryParameters, times(2)).getBeforeDate();
      verify(imageQueryParameters, times(2)).getTags();
      verify(imageQueryParameters, times(3)).getSteps();
      verify(imageQueryParameters, times(2)).getSampler();
      verify(imageQueryParameters, times(3)).getDenoise();
      verify(imageQueryParameters, times(3)).getCfg();
      verify(imageQueryParameters, times(2)).getModelHash();
      verify(imageQueryParameters, times(2)).getFaceRestoration();
      verify(imageQueryParameters, times(2)).getHypernet();
      verify(imageQueryParameters, times(3)).getClipSkip();
      verify(imageQueryParameters, times(3)).getWidth();
      verify(imageQueryParameters, times(3)).getHeight();
    }

    @Test
    @DisplayName("Given a query with no fields, verify all fields have been used")
    public void test1() {
      // Given a query with all fields
      ImageQueryParameters imageQueryParameters = mock(ImageQueryParameters.class);
      when(imageQueryParameters.getLocation()).thenReturn(null);
      when(imageQueryParameters.getAfterDate()).thenReturn(null);
      when(imageQueryParameters.getBeforeDate()).thenReturn(null);
      when(imageQueryParameters.getTags()).thenReturn(null);
      when(imageQueryParameters.getSteps()).thenReturn(null);
      when(imageQueryParameters.getSampler()).thenReturn(null);
      when(imageQueryParameters.getDenoise()).thenReturn(null);
      when(imageQueryParameters.getCfg()).thenReturn(null);
      when(imageQueryParameters.getModelHash()).thenReturn(null);
      when(imageQueryParameters.getFaceRestoration()).thenReturn(null);
      when(imageQueryParameters.getHypernet()).thenReturn(null);
      when(imageQueryParameters.getClipSkip()).thenReturn(null);
      when(imageQueryParameters.getWidth()).thenReturn(null);
      when(imageQueryParameters.getHeight()).thenReturn(null);
      when(imageQueryParameters.getCount()).thenReturn(null);

      doReturn(mock(AggregateIterable.class)).when(mongoCollection).aggregate(any());

      imageService.getImagesBy(imageQueryParameters);
      // Then verify all fields have been used
      verify(imageQueryParameters, times(1)).getLocation();
      verify(imageQueryParameters, times(1)).getAfterDate();
      verify(imageQueryParameters, times(1)).getBeforeDate();
      verify(imageQueryParameters, times(1)).getTags();
      verify(imageQueryParameters, times(1)).getSteps();
      verify(imageQueryParameters, times(1)).getSampler();
      verify(imageQueryParameters, times(1)).getDenoise();
      verify(imageQueryParameters, times(1)).getCfg();
      verify(imageQueryParameters, times(1)).getModelHash();
      verify(imageQueryParameters, times(1)).getFaceRestoration();
      verify(imageQueryParameters, times(1)).getHypernet();
      verify(imageQueryParameters, times(1)).getClipSkip();
      verify(imageQueryParameters, times(1)).getWidth();
      verify(imageQueryParameters, times(1)).getHeight();
    }
  }

  @Nested
  @DisplayName("findAll tests")
  class FindAllTest {

    @Test
    @DisplayName("Given method is called, verify findAll of ImageRepository is called")
    public void test0() {
      // Given method is called
      imageService.findAll();
      // Then verify findAll of ImageRepository is called
      verify(imageRepository, times(1)).findAll();
    }
  }

  @Nested
  @DisplayName("save tests")
  class SaveTest {

    @Test
    @DisplayName("Given image, verify save of ImageRepository is called")
    public void test0() {
      // Given image
      imageService.save(mock(Image.class));
      // Then verify save of ImageRepository is called
      verify(imageRepository, times(1)).save(any());
    }
  }

  @Nested
  @DisplayName("saveAll tests")
  class SaveAllTest {

    @Test
    @DisplayName("Given list of images, verify saveAll of ImageRepository is called")
    public void test0() {
      // Given list of images
      when(imageRepository.saveAll(anyIterable())).thenReturn(new ArrayList<>());

      imageService.saveAll(new ArrayList<>());
      // verify saveAll of ImageRepository is called
      verify(imageRepository, times(1)).saveAll(anyIterable());
    }
  }

  @Nested
  @DisplayName("count tests")
  class DeleteAllTest {

    @Test
    @DisplayName("Given list of images, verify count of ImageRepository is called")
    public void test0() {
      // Given list of images
      imageService.count();
      // verify count of ImageRepository is called
      verify(imageRepository, times(1)).count();
    }
  }

  @Nested
  @DisplayName("updateTags tests")
  class UpdateTagsTest {

    @Test
    @DisplayName("Given images have dog in its positive prompt, and it's also in the db, return the image with the same tags")
    public void test0() {
      // Given images have dog in its positive prompt, and it's also in the db
      List<String> mockTags = new ArrayList<>(List.of("dog"));
      List<Image> mockImages = new ArrayList<>(List.of(Image.builder()
          ._id(new ObjectId("666f6f2d6261722d71757578"))
          .positivePrompt("dog")
          .location("dummyLocation")
          .build()));
      when(tagService.findAll()).thenReturn(mockTags);
      when(imageService.findAll()).thenReturn(mockImages);
      when(imageService.saveAll(anyList())).thenReturn(mockImages);
      when(imageRepository.saveAll(anyList())).thenReturn(mockImages);
      when(imageRepository.findById(any())).thenReturn(Optional.ofNullable(mockImages.get(0)));

      imageService.updateTags();
      // Then return the image with the same tags
    }

    @Test
    @DisplayName("Given image have tag dog, but it's not in db, return the image without tags")
    public void test1() {
      // Given image have tag dog, but it's not in db
      List<Image> mockImages = new ArrayList<>(List.of(Image.builder()
          ._id(new ObjectId("666f6f2d6261722d71757578"))
          .positivePrompt("dog")
          .location("dummyLocation")
          .build()));
      when(tagService.findAll()).thenReturn(new ArrayList<>());
      when(imageService.findAll()).thenReturn(mockImages);
      when(imageService.saveAll(anyList())).thenReturn(mockImages);
      when(imageRepository.saveAll(anyList())).thenReturn(mockImages);
      when(imageRepository.findById(any())).thenReturn(Optional.ofNullable(mockImages.get(0)));

      imageService.updateTags();
      // Then return the image without tags
    }
  }
}
