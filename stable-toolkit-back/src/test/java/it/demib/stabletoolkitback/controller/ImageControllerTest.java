package it.demib.stabletoolkitback.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.demib.stabletoolkitback.model.dto.ImageDTO;
import it.demib.stabletoolkitback.model.dto.ImageQueryParameters;
import it.demib.stabletoolkitback.model.entity.Image;
import it.demib.stabletoolkitback.service.ImageService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ImageControllerTest {

  @InjectMocks
  private ImageController imageController;

  @Mock
  private ImageService imageService;

  @Nested
  @DisplayName("queryImages tests")
  class QueryImagesTest {

    @Test
    @DisplayName("Given parameters to query by, return the result of the query")
    public void test0() {
      // Given parameters to query by
      ImageDTO docs = mock(ImageDTO.class);

      when(imageService.getImagesBy(any())).thenReturn(List.of(docs));
      imageController.queryImages(ImageQueryParameters.builder().build());
      // Then return the result of the query
      verify(imageService, times(1)).getImagesBy(any());
    }
  }

  @Nested
  @DisplayName("updateImages tests")
  class UpdateImagesTest {

    @Test
    @DisplayName("Given parameters to query by, return the result of the query")
    public void test0() {
      // Given parameters to query by
      Image image = mock(Image.class);

      when(imageService.saveAll(anyList())).thenReturn(List.of(image));
      imageController.updateImages(List.of(image));
      // Then return the result of the query
      verify(imageService, times(1)).saveAll(anyList());
    }
  }
}
