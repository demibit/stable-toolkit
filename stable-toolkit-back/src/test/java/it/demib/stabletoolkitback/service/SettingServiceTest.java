package it.demib.stabletoolkitback.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.demib.stabletoolkitback.model.entity.Setting;
import it.demib.stabletoolkitback.repository.SettingRepository;
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
public class SettingServiceTest {

  @InjectMocks
  private SettingService settingService;

  @Mock
  private SettingRepository settingRepository;

  @Nested
  @DisplayName("getSettings tests")
  class GetSettingsTest {

    @Test
    @DisplayName("Given method is called, return all settings in db")
    public void test0() {
      // Given method is called
      var settings = List.of(mock(Setting.class));
      when(settingRepository.findAll()).thenReturn(settings);
      // Then return all settings in db
      assertThat(settingService.getSettings()).isEqualTo(settings);
    }
  }

  @Nested
  @DisplayName("addSettings tests")
  class AddSettingsTest {

    @Test
    @DisplayName("Given a list of valid settings, return the settings added to db")
    public void test0() {
      // Given a list of valid settings
      var settings = List.of(mock(Setting.class));
      when(settingRepository.saveAll(anyList())).thenReturn(settings);
      // Then return the settings added to db
      assertThat(settingService.addSettings(settings)).isEqualTo(settings);
    }
  }

  @Nested
  @DisplayName("deleteSettings tests")
  class DeleteSettingsTest {

    @Test
    @DisplayName("Given list of settings, verify deleteAll has been called")
    public void test0() {
      // Given list of settings
      var settings = List.of(mock(Setting.class));
      settingService.deleteSettings(settings);
      // Then verify deleteAll has been called
      verify(settingRepository, times(1)).deleteAll(anyList());
    }

    @Test
    @DisplayName("Given empty list of settings, verify all settings are deleted")
    public void test1() {
      // Given empty list of settings
      settingService.deleteSettings(new ArrayList<>());
      // Then verify all settings are deleted
      verify(settingRepository, times(1)).deleteAll();
    }
  }
}
