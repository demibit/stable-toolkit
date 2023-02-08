package it.demib.stabletoolkitback.service;

import it.demib.stabletoolkitback.model.entity.Folder;
import it.demib.stabletoolkitback.repository.FolderRepository;
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
public class FolderService {

  private final FolderRepository folderRepository;

  public List<Folder> getFolders() {
    return folderRepository.findAll();
  }

  public List<Folder> addFolders(List<Folder> folders) {
    return folderRepository.saveAll(folders);
  }

  protected void deleteFolders(List<Folder> folders) {
    if (folders.size() > 0) {
      log.info(LogMessageUtility.assembleLogMessage("FolderService", "deleteFolders",
          String.format("Deleting folders from database: %s",
              folders.stream()
                  .map(Folder::getName)
                  .collect(Collectors.joining(", ")))));

      folderRepository.deleteAllById(
          folders.stream().map(Folder::get_id).collect(Collectors.toList()));
    } else {
      log.info(LogMessageUtility.assembleLogMessage("FolderService", "deleteFolders", "Deleting all folders from database"));

      folderRepository.deleteAll();
    }
  }
}
