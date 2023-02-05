package it.demib.stabletoolkitback.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import it.demib.stabletoolkitback.model.entity.Folder;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class ImageQueryParameters {

  private List<Folder> location;
  private Integer count;
  private List<String> tags;
  private List<Integer> steps;
  private List<String> sampler;
  private List<Double> denoise;
  private List<Double> cfg;
  private List<String> modelHash;
  private List<String> modelName;
  private List<String> faceRestoration;
  private List<String> hypernet;
  private List<Integer> clipSkip;
  private List<Integer> width;
  private List<Integer> height;
  private Instant afterDate;
  private Instant beforeDate;
}
