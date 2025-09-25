package io.spring.core.task;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TaskNote {
  private String id;
  private String body;
  private String userId;
  private String taskId;
  private DateTime createdAt;

  public TaskNote(String body, String userId, String taskId) {
    this.id = UUID.randomUUID().toString();
    this.body = body;
    this.userId = userId;
    this.taskId = taskId;
    this.createdAt = new DateTime();
  }
}
