package it.demib.stabletoolkitback.service;

import it.demib.stabletoolkitback.constant.ImageType;
import it.demib.stabletoolkitback.model.entity.Folder;
import it.demib.stabletoolkitback.model.entity.Image;
import it.demib.stabletoolkitback.utility.GenerationInformationTextParser;
import it.demib.stabletoolkitback.utility.LogMessageUtility;
import it.demib.stabletoolkitback.utility.PNGTextExtractor;
import it.demib.stabletoolkitback.utility.TagParser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class IndexService {

  private final PNGTextExtractor pngTextExtractor;

  private final FolderService folderService;

  private final SettingService settingService;

  private final ImageService imageService;

  private final TagService tagService;

  private final GenerationInformationTextParser generationInformationTextParser;

  private final TagParser tagParser;

  @Scheduled(fixedDelay = 10_000)
  public void index() {
    List<Folder> folders = folderService.getFolders();

    folders.stream()
        .parallel()
        .forEach(this::indexFolder);
  }

  private void indexFolder(Folder folder) {
    List<Image> images = imageService.findAll();
    Set<String> imageFileNameSet = images.stream()
        .map(Image::getFileName)
        .collect(Collectors.toSet());

    List<String> currentTags = tagService.findAll();

    long sizeBefore = imageService.count();

    try (Stream<Path> paths = Files.walk(Paths.get(folder.getPath()))) {
      List<Path> pathsToIndex = paths
          .filter(path -> Files.isRegularFile(path) && !imageFileNameSet.contains(
              path.getFileName().toString()) && path.getFileName().toString()
              .endsWith(ImageType.PNG))
          .toList();

      if (!pathsToIndex.isEmpty()) {
        log.info(LogMessageUtility.assembleLogMessage("IndexService", "indexFolder",
            String.format("Indexing folder for new images: %s", folder.getName())));

        pathsToIndex.stream()
            .parallel()
            .forEach(path -> indexImage(path, currentTags, folder));
      }

    } catch (Exception e) {
      log.error(LogMessageUtility.assembleLogMessage("IndexService", "indexFolder",
          "Unable to index images"));
      e.printStackTrace();
      throw new RuntimeException(e);
    }

    long sizeAfter = imageService.count();

    if (sizeAfter > sizeBefore || sizeAfter < sizeBefore) {
      imageService.updateTags();
      log.info(LogMessageUtility.assembleLogMessage("IndexService", "indexFolder",
          String.format("Finished indexing folder: %s | total image count: %s", folder.getName(),
              sizeAfter)));
    }
  }

  private void indexImage(Path path, List<String> currentTags, Folder folder) {
    String currentFileName = path.getFileName().toString();

    log.info(LogMessageUtility.assembleLogMessage("IndexService", "indexImage",
        String.format("Indexing image %s", currentFileName)));

    List<String> tagsToAssign = new ArrayList<>();
    Map<String, String> info = new HashMap<>();
    List<String> textData = new ArrayList<>();
    BufferedImage bimg = null;

    try {
      File img = new File(path.toUri());
      bimg = ImageIO.read(img);
      textData = Arrays.asList(
          pngTextExtractor.showText(new FileInputStream(img)));
    } catch (Exception e) {
      log.warn(LogMessageUtility.assembleLogMessage("IndexService", "indexImage",
          String.format("Unable to read the image chunks of %s, continuing without it",
              currentFileName)));
    }

    if (textData.size() >= 3) {
      tagsToAssign = tagParser.parse(Pair.of(currentTags, textData.get(0)));
      info = generationInformationTextParser.parse(textData);
    }

    Instant creationDate = null;
    Integer width = null;
    Integer height = null;
    try {
      creationDate = Files.readAttributes(path, BasicFileAttributes.class)
          .creationTime()
          .toInstant();
    } catch (Exception e) {
      log.warn(LogMessageUtility.assembleLogMessage("IndexService", "indexImage",
          String.format("Unable to read the creation date of %s, continuing without it",
              currentFileName)));
    }

    try {
      Objects.requireNonNull(bimg);
      width = bimg.getWidth();
      height = bimg.getHeight();
    } catch (Exception e) {
      log.warn(LogMessageUtility.assembleLogMessage("IndexService", "indexImage",
          String.format(
              "Unable to read the image dimensions of %s, continuing without it",
              currentFileName)));
    }

    imageService.save(Image.builder()
        .location(folder.getPath())
        .fileName(path.getFileName().toString())
        .creationDate(creationDate)
        .tags(tagsToAssign)
        .positivePrompt(info.get("Positive prompt"))
        .negativePrompt(info.get("Negative prompt"))
        .steps(Objects.nonNull(info.get("Steps")) ? Integer.valueOf(info.get("Steps"))
            : null)
        .sampler(info.get("Sampler"))
        .denoise(Objects.nonNull(info.get("Denoising strength")) ? Double.valueOf(
            info.get("Denoising strength")) : null)
        .cfg(
            Objects.nonNull(info.get("CFG scale")) ? Double.valueOf(
                info.get("CFG scale"))
                : null)
        .modelHash(info.get("Model hash"))
        .modelName(info.get("Model"))
        .faceRestoration(info.get("Face restoration"))
        .hypernet(info.get("Hypernet"))
        .clipSkip(Objects.nonNull(info.get("Clip skip")) ? Integer.valueOf(
            info.get("Clip skip")) : null)
        .width(width)
        .height(height)
        .build());

  }
}
