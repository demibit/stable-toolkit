package it.demib.stabletoolkitback.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.demib.stabletoolkitback.model.entity.Folder;
import it.demib.stabletoolkitback.service.FolderService;
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
public class FolderControllerTest {

  @InjectMocks
  private FolderController folderController;

  @Mock
  private FolderService folderService;

  @Mock
  private ImageService imageService;

  @Nested
  @DisplayName("getFolders tests")
  class GetFoldersTest {

    @Test
    @DisplayName("Given settings are queried and present, return existing settings")
    public void test0() {
      // Given settings are queried
      var mockObject = mock(Folder.class);
      when(folderService.getFolders()).thenReturn(List.of(mockObject));
      // Then return existing settings
      assertThat(folderController.getFolders().get(0)).isEqualTo(mockObject);
    }
  }

  @Nested
  @DisplayName("addFolders tests")
  class AddFoldersTest {

    @Test
    @DisplayName("Given a list of valid settings, return the settings added to db")
    public void test0() {
      // Given a list of valid settings
      var settings = List.of(mock(Folder.class));
      when(folderService.addFolders(anyList())).thenReturn(settings);
      // Then return the settings added to db
      assertThat(folderController.addFolders(settings)).isEqualTo(settings);
    }
  }

  @Nested
  @DisplayName("deleteFolders tests")
  class DeleteFoldersTest {

    @Test
    @DisplayName("Given list of settings, verify deleteFolders has been called")
    public void test0() {
      // Given list of settings
      var settings = List.of(mock(Folder.class));
      folderController.deleteFolders(settings);
      // Then verify deleteFolders has been called
      verify(imageService, times(1)).deleteAllInFolder(anyList());
    }
  }
}
