package io.spring.application.task;

import io.spring.core.task.TaskPriority;
import io.spring.core.task.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTaskParam {
  private String title;
  private String description;
  private String notes;
  private TaskStatus status;
  private TaskPriority priority;
  private DateTime dueDate;
  private DateTime reminderDate;
}
