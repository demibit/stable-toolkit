package it.demib.stabletoolkitback.controller;

import it.demib.stabletoolkitback.model.entity.Setting;
import it.demib.stabletoolkitback.service.SettingService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setting")
@AllArgsConstructor(onConstructor_ = @Autowired)
@CrossOrigin
public class SettingController {

  private final SettingService settingService;

  @GetMapping
  public List<Setting> getSettings() {
    return settingService.getSettings();
  }

  @PutMapping
  public List<Setting> addSettings(@RequestBody List<Setting> settings) {
    return settingService.addSettings(settings);
  }

  @DeleteMapping
  public Boolean deleteSettings(@RequestBody List<Setting> settings) {
    return settingService.deleteSettings(settings);
  }
}
