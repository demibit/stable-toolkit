package it.demib.stabletoolkitback.service;

import it.demib.stabletoolkitback.model.entity.Setting;
import it.demib.stabletoolkitback.repository.SettingRepository;
import it.demib.stabletoolkitback.utility.LogMessageUtility;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class SettingService {

  private final SettingRepository settingRepository;

  public List<Setting> getSettings() {
    return settingRepository.findAll();
  }

  public List<Setting> addSettings(List<Setting> settings) {
    return settingRepository.saveAll(settings);
  }

  public Boolean deleteSettings(List<Setting> settings) {
    if (settings.size() > 0) {
      log.info(LogMessageUtility.assembleLogMessage("SettingService", "deleteSettings",
          String.format("Deleting settings from database: %s",
              settings.stream()
                  .map(Setting::getName)
                  .collect(Collectors.joining(", ")))));

      settingRepository.deleteAll(settings);
    } else {
      log.info(LogMessageUtility.assembleLogMessage("SettingService", "deleteSettings",
          "Deleting all settings from database"));

      settingRepository.deleteAll();
    }

    return true;
  }
}

