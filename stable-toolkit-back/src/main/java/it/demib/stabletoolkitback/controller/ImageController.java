package it.demib.stabletoolkitback.controller;

import it.demib.stabletoolkitback.model.dto.ImageDTO;
import it.demib.stabletoolkitback.model.dto.ImageQueryParameters;
import it.demib.stabletoolkitback.model.entity.Image;
import it.demib.stabletoolkitback.service.ImageService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
@AllArgsConstructor(onConstructor_ = @Autowired)
@CrossOrigin
public class ImageController {

  private final ImageService imageService;

  @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
  public ResponseEntity<Resource> getImage(@RequestParam String url) throws IOException {
    String strippedUrl = url.split("\\?")[0];

    final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
        strippedUrl
    )));
    return ResponseEntity
        .status(HttpStatus.OK)
        .contentLength(inputStream.contentLength())
        .body(inputStream);
  }

  @DeleteMapping
  public Boolean triggerReIndex() {
    imageService.deleteAll();

    return true;
  }
  @PutMapping
  public List<Image> updateImages(@RequestBody List<Image> images) {
    return imageService.saveAll(images);
  }

  @GetMapping("/filter")
  public ImageDTO getFilters() {
    return imageService.getFilters();
  }

  @PostMapping("/filter")
  public List<ImageDTO> queryImages(@RequestBody ImageQueryParameters imageQueryParameters) {
    return imageService.getImagesBy(imageQueryParameters);
  }

}
