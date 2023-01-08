package it.demib.stabletoolkitback.controller;

import it.demib.stabletoolkitback.service.TagService;
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
@RequestMapping("/tag")
@AllArgsConstructor(onConstructor_ = @Autowired)
@CrossOrigin
public class TagController {

  private final TagService tagService;

  @GetMapping
  public List<String> getTags() {
    return tagService.findAll();
  }

  @PutMapping
  public List<String> addTags(@RequestBody List<String> tags) {
    return tagService.addTags(tags);
  }

  @DeleteMapping
  public List<String> deleteTags(@RequestBody List<String> tags) {
    return tagService.deleteTags(tags);
  }
}
