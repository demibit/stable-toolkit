package it.demib.stabletoolkitback.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.demib.stabletoolkitback.model.entity.Setting;
import it.demib.stabletoolkitback.service.SettingService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SettingControllerTest {

  @InjectMocks
  private SettingController settingController;

  @Mock
  private SettingService settingService;

  @Nested
  @DisplayName("getSettings tests")
  class GetSettingsTest {

    @Test
    @DisplayName("Given settings are queried and present, return existing settings")
    public void test0() {
      // Given settings are queried
      var mockObject = mock(Setting.class);
      when(settingService.getSettings()).thenReturn(List.of(mockObject));
      // Then return existing settings
      assertThat(settingController.getSettings().get(0)).isEqualTo(mockObject);
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
      when(settingService.addSettings(anyList())).thenReturn(settings);
      // Then return the settings added to db
      assertThat(settingController.addSettings(settings)).isEqualTo(settings);
    }
  }

  @Nested
  @DisplayName("deleteSettings tests")
  class DeleteSettingsTest {

    @Test
    @DisplayName("Given list of settings, verify deleteSettings has been called")
    public void test0() {
      // Given list of settings
      var settings = List.of(mock(Setting.class));
      when(settingService.deleteSettings(anyList())).thenReturn(true);
      settingController.deleteSettings(settings);
      // Then verify deleteSettings has been called
      verify(settingService, times(1)).deleteSettings(anyList());
    }
  }
}
