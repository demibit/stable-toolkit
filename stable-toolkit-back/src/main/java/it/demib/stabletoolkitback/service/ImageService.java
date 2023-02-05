package it.demib.stabletoolkitback.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import it.demib.stabletoolkitback.model.dto.ImageDTO;
import it.demib.stabletoolkitback.model.dto.ImageQueryParameters;
import it.demib.stabletoolkitback.model.dto.MoveDTO;
import it.demib.stabletoolkitback.model.entity.Folder;
import it.demib.stabletoolkitback.model.entity.Image;
import it.demib.stabletoolkitback.repository.ImageRepository;
import it.demib.stabletoolkitback.utility.TagParser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ImageService {

  private final ImageRepository imageRepository;

  @Qualifier("IMAGE")
  private final MongoCollection<Document> mongoCollection;

  private final TagService tagService;

  private final FolderService folderService;

  public ByteArrayResource getImage(String id) {
    try {
      ObjectId parsedObjectId = new ObjectId(id.split("\\?")[0]);

      Image foundImage = imageRepository.findById(parsedObjectId).get();

      String imageUrl = String.format("%s\\%s", foundImage.getLocation(), foundImage.getFileName());

      return new ByteArrayResource(Files.readAllBytes(Paths.get(
          imageUrl
      )));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public ImageQueryParameters getFilters() {
    List<Image> images = imageRepository.findAll();

    List<String> location = new ArrayList<>();
    List<Instant> creationDate = new ArrayList<>(List.of(Instant.MAX, Instant.MIN));
    List<Integer> steps = new ArrayList<>(List.of(Integer.MAX_VALUE, Integer.MIN_VALUE));
    List<String> sampler = new ArrayList<>();
    List<Double> denoise = new ArrayList<>(List.of(Double.MAX_VALUE, Double.MIN_VALUE));
    List<Double> cfg = new ArrayList<>(List.of(Double.MAX_VALUE, Double.MIN_VALUE));
    List<String> modelHash = new ArrayList<>();
    List<String> modelName = new ArrayList<>();
    List<String> faceRestoration = new ArrayList<>();
    List<String> hypernet = new ArrayList<>();
    List<Integer> clipSkip = new ArrayList<>(
        new ArrayList<>(List.of(Integer.MAX_VALUE, Integer.MIN_VALUE)));
    List<Integer> width = new ArrayList<>(List.of(Integer.MAX_VALUE, Integer.MIN_VALUE));
    List<Integer> height = new ArrayList<>(List.of(Integer.MAX_VALUE, Integer.MIN_VALUE));

    for (Image img : images) {
      if (Objects.nonNull(img.getLocation()) && !location.contains(img.getLocation())) {
        location.add(img.getLocation());
      }
      if (Objects.nonNull(img.getCreationDate())) {
        if (img.getCreationDate().isBefore(creationDate.get(0))) {
          creationDate.set(0, img.getCreationDate());
        }
        if (img.getCreationDate().isAfter(creationDate.get(1))) {
          creationDate.set(1, img.getCreationDate());
        }
      }
      if (Objects.nonNull(img.getSteps())) {
        if (img.getSteps() < steps.get(0)) {
          steps.set(0, img.getSteps());
        }
        if (img.getSteps() > steps.get(1)) {
          steps.set(1, img.getSteps());
        }
      }
      if (Objects.nonNull(img.getSampler()) && !sampler.contains(img.getSampler())) {
        sampler.add(img.getSampler());
      }
      if (Objects.nonNull(img.getDenoise())) {
        if (img.getDenoise() < denoise.get(0)) {
          denoise.set(0, img.getDenoise());
        }
        if (img.getDenoise() > denoise.get(1)) {
          denoise.set(1, img.getDenoise());
        }
      }
      if (Objects.nonNull(img.getCfg())) {
        if (img.getCfg() < cfg.get(0)) {
          cfg.set(0, img.getCfg());
        }
        if (img.getCfg() > cfg.get(1)) {
          cfg.set(1, img.getCfg());
        }
      }
      if (Objects.nonNull(img.getModelHash()) && !modelHash.contains(img.getModelHash())) {
        modelHash.add(img.getModelHash());
      }
      if (Objects.nonNull(img.getModelName()) && !modelHash.contains(img.getModelName())) {
        modelName.add(img.getModelName());
      }
      if (Objects.nonNull(img.getFaceRestoration()) && !faceRestoration.contains(
          img.getFaceRestoration())) {
        faceRestoration.add(img.getFaceRestoration());
      }
      if (Objects.nonNull(img.getHypernet()) && !hypernet.contains(img.getHypernet())) {
        hypernet.add(img.getHypernet());
      }
      if (Objects.nonNull(img.getClipSkip())) {
        if (img.getClipSkip() < clipSkip.get(0)) {
          clipSkip.set(0, img.getClipSkip());
        }
        if (img.getClipSkip() > clipSkip.get(1)) {
          clipSkip.set(1, img.getClipSkip());
        }
      }
      if (Objects.nonNull(img.getWidth())) {
        if (img.getWidth() < width.get(0)) {
          width.set(0, img.getWidth());
        }
        if (img.getWidth() > width.get(1)) {
          width.set(1, img.getWidth());
        }
      }
      if (Objects.nonNull(img.getHeight())) {
        if (img.getHeight() < height.get(0)) {
          height.set(0, img.getHeight());
        }
        if (img.getHeight() > height.get(1)) {
          height.set(1, img.getHeight());
        }
      }
    }

    List<String> tags = tagService.findAll();

    return ImageQueryParameters.builder()
        .location(folderService.getFolders())
        .afterDate(isInstantUnchanged(creationDate) ? null : creationDate.get(1).minus(1L, ChronoUnit.MINUTES))
        .beforeDate(isInstantUnchanged(creationDate) ? null : creationDate.get(0).plus(1L, ChronoUnit.MINUTES))
        .tags(tags.isEmpty() ? null : tags)
        .steps(isIntegerUnchanged(steps) ? null : steps)
        .sampler(sampler.isEmpty() ? null : sampler)
        .denoise(isDoubleUnchanged(denoise) ? null : denoise)
        .cfg(isDoubleUnchanged(cfg) ? null : cfg)
        .modelHash(modelHash.isEmpty() ? null : modelHash)
        .modelName(modelName.isEmpty() ? null : modelName)
        .faceRestoration(faceRestoration.isEmpty() ? null : faceRestoration)
        .hypernet(hypernet.isEmpty() ? null : hypernet)
        .clipSkip(isIntegerUnchanged(clipSkip) ? null : clipSkip)
        .width(isIntegerUnchanged(width) ? null : width)
        .height(isIntegerUnchanged(height) ? null : height)
        .build();
  }

  private boolean isInstantUnchanged(List<Instant> list) {
    return list.contains(Instant.MAX) || list.contains(Instant.MIN);
  }

  private boolean isIntegerUnchanged(List<Integer> list) {
    return list.contains(Integer.MAX_VALUE) || list.contains(Integer.MIN_VALUE);
  }

  private boolean isDoubleUnchanged(List<Double> list) {
    return list.get(0).equals(Double.MAX_VALUE) || list.get(1).equals(Double.MIN_VALUE);
  }

  public List<ImageDTO> getImagesBy(ImageQueryParameters imageQueryParameters) {

    List<Bson> toAggregateBy = new LinkedList<>();

    if (Objects.nonNull(imageQueryParameters.getLocation())) {
      toAggregateBy.add(
          Aggregates.match(
              Filters.in("location", imageQueryParameters.getLocation().stream()
                  .map(Folder::getPath)
                  .collect(Collectors.toList()))));
    }
    if (Objects.nonNull(imageQueryParameters.getAfterDate())) {
      toAggregateBy.add(
          Aggregates.match(Filters.gte("creationDate", imageQueryParameters.getAfterDate())));

    }
    if (Objects.nonNull(imageQueryParameters.getBeforeDate())) {
      toAggregateBy.add(
          Aggregates.match(Filters.lte("creationDate", imageQueryParameters.getBeforeDate())));
    }
    if (Objects.nonNull(imageQueryParameters.getTags())) {
      toAggregateBy.add(Aggregates.match(Filters.all("tags", imageQueryParameters.getTags())));
    }
    if (Objects.nonNull(imageQueryParameters.getSteps())) {
      toAggregateBy.add(
          Aggregates.match(Filters.gte("steps", imageQueryParameters.getSteps()
              .stream()
              .min(Integer::compare)
              .orElseThrow())));
      toAggregateBy.add(
          Aggregates.match(Filters.lte("steps", imageQueryParameters.getSteps()
              .stream()
              .max(Integer::compare)
              .orElseThrow())));
    }
    if (Objects.nonNull(imageQueryParameters.getSampler())) {
      toAggregateBy.add(
          Aggregates.match(Filters.in("sampler", imageQueryParameters.getSampler())));
    }
    if (Objects.nonNull(imageQueryParameters.getDenoise())) {
      toAggregateBy.add(
          Aggregates.match(Filters.gte("denoise", imageQueryParameters.getDenoise()
              .stream()
              .min(Double::compare)
              .orElseThrow())));
      toAggregateBy.add(
          Aggregates.match(Filters.lte("denoise", imageQueryParameters.getDenoise()
              .stream()
              .max(Double::compare)
              .orElseThrow())));
    }
    if (Objects.nonNull(imageQueryParameters.getCfg())) {
      toAggregateBy.add(
          Aggregates.match(Filters.gte("cfg", imageQueryParameters.getCfg()
              .stream()
              .min(Double::compare)
              .orElseThrow())));
      toAggregateBy.add(
          Aggregates.match(Filters.lte("cfg", imageQueryParameters.getCfg()
              .stream()
              .max(Double::compare)
              .orElseThrow())));
    }
    if (Objects.nonNull(imageQueryParameters.getModelHash())) {
      toAggregateBy.add(
          Aggregates.match(Filters.in("modelHash", imageQueryParameters.getModelHash())));
    }
    if (Objects.nonNull(imageQueryParameters.getModelName())) {
      toAggregateBy.add(
          Aggregates.match(Filters.in("modelName", imageQueryParameters.getModelName())));
    }
    if (Objects.nonNull(imageQueryParameters.getFaceRestoration())) {
      toAggregateBy.add(
          Aggregates.match(
              Filters.in("faceRestoration", imageQueryParameters.getFaceRestoration())));
    }
    if (Objects.nonNull(imageQueryParameters.getHypernet())) {
      toAggregateBy.add(
          Aggregates.match(Filters.in("hypernet", imageQueryParameters.getHypernet())));
    }
    if (Objects.nonNull(imageQueryParameters.getClipSkip())) {
      toAggregateBy.add(
          Aggregates.match(Filters.gte("clipSkip", imageQueryParameters.getClipSkip()
              .stream()
              .min(Integer::compare)
              .orElseThrow())));
      toAggregateBy.add(
          Aggregates.match(Filters.lte("clipSkip", imageQueryParameters.getClipSkip()
              .stream()
              .max(Integer::compare)
              .orElseThrow())));
    }
    if (Objects.nonNull(imageQueryParameters.getWidth())) {
      toAggregateBy.add(
          Aggregates.match(Filters.gte("width", imageQueryParameters.getWidth()
              .stream()
              .min(Integer::compare)
              .orElseThrow())));
      toAggregateBy.add(
          Aggregates.match(Filters.lte("width", imageQueryParameters.getWidth()
              .stream()
              .max(Integer::compare)
              .orElseThrow())));
    }
    if (Objects.nonNull(imageQueryParameters.getHeight())) {
      toAggregateBy.add(
          Aggregates.match(Filters.gte("height", imageQueryParameters.getHeight()
              .stream()
              .min(Integer::compare)
              .orElseThrow())));
      toAggregateBy.add(
          Aggregates.match(Filters.lte("height", imageQueryParameters.getHeight()
              .stream()
              .max(Integer::compare)
              .orElseThrow())));
    }

    toAggregateBy.add(Aggregates.sort(
        Sorts.descending("creationDate")
    ));

    List<Document> filteredList = mongoCollection.aggregate(toAggregateBy).into(new ArrayList<>());

    return filteredList.stream().map(document ->
        ImageDTO.builder()
            ._id(((ObjectId) document.get("_id")).toString())
            .location(document.get("location"))
            .fileName(document.get("fileName"))
            .creationDate(document.get("creationDate"))
            .tags(document.get("tags"))
            .positivePrompt(document.get("positivePrompt"))
            .negativePrompt(document.get("negativePrompt"))
            .steps(document.get("steps"))
            .sampler(document.get("sampler"))
            .denoise(document.get("denoise"))
            .cfg(document.get("cfg"))
            .modelHash(document.get("modelHash"))
            .modelName(document.get("modelName"))
            .faceRestoration(document.get("faceRestoration"))
            .hypernet(document.get("hypernet"))
            .clipSkip(document.get("clipSkip"))
            .width(document.get("width"))
            .height(document.get("height"))
            .build()
    ).toList().subList(0,
        Objects.nonNull(imageQueryParameters.getCount())
            && filteredList.size() > imageQueryParameters.getCount()
            ? imageQueryParameters.getCount()
            : filteredList.size());
  }

  public List<Image> findAll() {
    return imageRepository.findAll();
  }

  protected Image save(Image image) {
    return imageRepository.save(image);
  }

  protected void updateTags() {
    List<Image> currentImages = findAll();
    List<String> currentUserTags = tagService.findAll();

    if (currentUserTags.size() > 0) {
      for (Image image : currentImages) {
        image.setTags(
            currentUserTags.stream().filter(s -> TagParser.isContain(image.getPositivePrompt(), s))
                .collect(Collectors.toList()));
      }
      saveAll(currentImages);
    }
  }

  public void findImageInFolder(String path) {
    try {
      String treatedPath = path.split("\\?")[0].replace("/", "\\");
      new ProcessBuilder().command("explorer.exe", "/select,", treatedPath).start();
    } catch (Exception e) {
      log.warn("Unable to find the specified image at path: {}", path);
    }
  }

  public List<Image> saveAll(List<Image> images) {
    List<Pair<Image, MoveDTO>> newListOfImages = images.stream().filter(image ->
            !imageRepository
                .findById(image.get_id())
                .orElseThrow()
                .getLocation()
                .equals(image.getLocation()))
        .map(image -> Pair.of(image.toBuilder()
                .fileName(image.getFileName())
                .build(),
            MoveDTO.builder()
                .from(imageRepository.findById(image.get_id()).orElseThrow().getLocation())
                .to(image.getLocation())
                .fileName(image.getFileName())
                .newFileName(image.getFileName())
                .build())).toList();

    newListOfImages.stream()
        .parallel()
        .forEach(imageMoveDTOPair -> moveImage(imageMoveDTOPair.getSecond()));

    return imageRepository.saveAll(
        newListOfImages.stream().map(Pair::getFirst).collect(Collectors.toList()));
  }

  protected long count() {
    return imageRepository.count();
  }

  public void deleteAll() {
    imageRepository.deleteAll();
  }

  public void deleteAllInFolder(List<Folder> folders) {
    folderService.deleteFolders(folders);
    folders.stream().map(Folder::getPath).forEach(this::deleteInFolder);
  }

  private void deleteInFolder(String folder) {
    imageRepository.deleteAllById(mongoCollection.aggregate(
            List.of(
                Aggregates.match(
                    Filters.eq("location", folder)
                )
            )).into(new ArrayList<>())
        .stream()
        .map(document -> ((ObjectId) document.get("_id")))
        .toList());
  }

  private void moveImage(MoveDTO moveDTO) {
    try (Stream<Path> paths = Files.walk(
        Paths.get(moveDTO.getFrom()))) {
      paths
          .filter(Files::isRegularFile)
          .filter(path -> path.toString().contains(moveDTO.getFileName()))
          .forEach(path -> {
            try {
              log.info("Moving {} to {}",
                  path,
                  moveDTO.getTo() + "\\" + moveDTO.getNewFileName());
              FileUtils.moveFile(
                  path.toFile(),
                  new File(moveDTO.getTo() + "\\" + moveDTO.getNewFileName()));

            } catch (IOException e) {
              log.error("Unable to move {}", path);
              throw new RuntimeException(e);
            }
          });
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
