package it.demib.stabletoolkitback.service;

import it.demib.stabletoolkitback.model.entity.Folder;
import it.demib.stabletoolkitback.repository.FolderRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
      folderRepository.deleteAllById(folders.stream().map(Folder::get_id).collect(Collectors.toList()));
    } else {
      folderRepository.deleteAll();
    }
  }
}
