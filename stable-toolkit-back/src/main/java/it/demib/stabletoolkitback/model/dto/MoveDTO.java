package it.demib.stabletoolkitback.model.dto;

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
public class MoveDTO {
  String from;
  String to;
  String fileName;
  String newFileName;
}
