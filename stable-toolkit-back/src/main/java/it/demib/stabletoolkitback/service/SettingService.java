package it.demib.stabletoolkitback.service;

import it.demib.stabletoolkitback.model.entity.Setting;
import it.demib.stabletoolkitback.repository.SettingRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
      settingRepository.deleteAll(settings);
    } else {
      settingRepository.deleteAll();
    }

    return true;
  }
}

