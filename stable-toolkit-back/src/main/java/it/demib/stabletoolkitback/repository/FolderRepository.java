package it.demib.stabletoolkitback.repository;

import it.demib.stabletoolkitback.model.entity.Folder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends MongoRepository<Folder, String> {

}
