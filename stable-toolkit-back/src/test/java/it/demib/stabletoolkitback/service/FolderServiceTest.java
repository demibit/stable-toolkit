package it.demib.stabletoolkitback.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.demib.stabletoolkitback.model.entity.Folder;
import it.demib.stabletoolkitback.repository.FolderRepository;
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
public class FolderServiceTest {

  @InjectMocks
  private FolderService folderService;

  @Mock
  private FolderRepository folderRepository;

  @Nested
  @DisplayName("getFolders tests")
  class GetFoldersTest {

    @Test
    @DisplayName("Given method is called, return all settings in db")
    public void test0() {
      // Given method is called
      var settings = List.of(mock(Folder.class));
      when(folderRepository.findAll()).thenReturn(settings);
      // Then return all settings in db
      assertThat(folderService.getFolders()).isEqualTo(settings);
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
      when(folderRepository.saveAll(anyList())).thenReturn(settings);
      // Then return the settings added to db
      assertThat(folderService.addFolders(settings)).isEqualTo(settings);
    }
  }

  @Nested
  @DisplayName("deleteFolders tests")
  class DeleteFoldersTest {

    @Test
    @DisplayName("Given list of settings, verify deleteAllById has been called")
    public void test0() {
      // Given list of settings
      var settings = List.of(mock(Folder.class));
      folderService.deleteFolders(settings);
      // Then verify deleteAllById has been called
      verify(folderRepository, times(1)).deleteAllById(anyList());
    }

    @Test
    @DisplayName("Given empty list of settings, verify all settings are deleted")
    public void test1() {
      // Given empty list of settings
      folderService.deleteFolders(new ArrayList<>());
      // Then verify all settings are deleted
      verify(folderRepository, times(1)).deleteAll();
    }
  }
}
