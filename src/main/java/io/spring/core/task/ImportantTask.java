package io.spring.core.task;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class ImportantTask {
  private String taskId;
  private String userId;

  public ImportantTask(String taskId, String userId) {
    this.taskId = taskId;
    this.userId = userId;
  }
}
