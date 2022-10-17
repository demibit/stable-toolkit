package it.demib.stabletoolkitback.service;

import it.demib.stabletoolkitback.model.entity.Image;
import it.demib.stabletoolkitback.model.entity.TagHolder;
import it.demib.stabletoolkitback.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class TagService {

  private final TagRepository tagRepository;


  public List<String> findAll() {
    return parseTagHolderList(tagRepository.findAll()).getTags();
  }

  public List<String> addTags(List<String> tags) {
    TagHolder tagHolder = parseTagHolderList(tagRepository.findAll());
    List<String> currentTags = tagHolder.getTags();

    tags.forEach(s -> {
      if (!currentTags.contains(s)) {
        currentTags.add(s);
      }
    });

    return tagRepository.save(tagHolder.toBuilder()
        .tags(currentTags)
        .build()).getTags();
  }

  public List<String> deleteTags(List<String> tags) {
    if (tags.size() == 0) {
      tagRepository.deleteAll();
      return new ArrayList<>();
    }

    TagHolder tagHolder = parseTagHolderList(tagRepository.findAll());
    List<String> currentTags = tagHolder.getTags();

    return tagRepository.save(tagHolder.toBuilder()
            .tags(currentTags.stream()
                .filter(s -> !tags.contains(s))
                .collect(Collectors.toList()))
            .build())
        .getTags();
  }

  private TagHolder parseTagHolderList(List<TagHolder> tags) {
    return Objects.nonNull(tags)
        && tags.size() > 0
        && Objects.nonNull(tags.get(0))
        && Objects.nonNull(tags.get(0).getTags())
        ? tags.get(0)
        : tagRepository.save(TagHolder.builder()
            .tags(new ArrayList<>())
            .build());
  }
}
