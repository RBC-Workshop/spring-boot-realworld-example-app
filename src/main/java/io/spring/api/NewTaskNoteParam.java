package io.spring.api;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewTaskNoteParam {
  @NotBlank(message = "can't be empty")
  private String body;
}
