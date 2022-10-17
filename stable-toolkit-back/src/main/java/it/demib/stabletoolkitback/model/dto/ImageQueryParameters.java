package it.demib.stabletoolkitback.model.dto;


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
public class ImageQueryParameters {

  private List<String> location;
  private Integer count;
  private List<String> tags;
  private List<Integer> steps;
  private List<String> sampler;
  private List<Double> denoise;
  private List<Double> cfg;
  private List<String> modelHash;
  private List<String> faceRestoration;
  private List<String> hypernet;
  private List<Integer> clipSkip;
  private List<Integer> width;
  private List<Integer> height;
  private Instant afterDate;
  private Instant beforeDate;
}
