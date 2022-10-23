package it.demib.stabletoolkitback.model.entity;


import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document
public class Image {

  @Id
  private ObjectId _id;

  private String location;
  private String fileName;
  private Instant creationDate;
  private List<String> tags;
  private String positivePrompt;
  private String negativePrompt;
  private Integer steps;
  private String sampler;
  private Double denoise;
  private Double cfg;
  private String modelHash;
  private String faceRestoration;
  private String hypernet;
  private Integer clipSkip;
  private Integer width;
  private Integer height;
}
