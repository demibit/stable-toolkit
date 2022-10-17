package it.demib.stabletoolkitback.controller;

import it.demib.stabletoolkitback.model.entity.Folder;
import it.demib.stabletoolkitback.service.FolderService;
import it.demib.stabletoolkitback.service.ImageService;
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
@RequestMapping("/folder")
@AllArgsConstructor(onConstructor_ = @Autowired)
@CrossOrigin
public class FolderController {

  private final FolderService folderService;
  private final ImageService imageService;

  @GetMapping
  public List<Folder> getFolders() {
    return folderService.getFolders();
  }

  @PutMapping
  public List<Folder> addFolders(@RequestBody List<Folder> folders) {
    return folderService.addFolders(folders);
  }

  @DeleteMapping
  public Boolean deleteFolders(@RequestBody List<Folder> folders) {
    imageService.deleteAllInFolder(folders);

    return true;
  }
}
