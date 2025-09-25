package io.spring.application.task;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.spring.core.task.TaskPriority;
import io.spring.core.task.TaskStatus;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Getter
@JsonRootName("task")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewTaskParam {
  @NotBlank(message = "can't be empty")
  private String title;

  @NotBlank(message = "can't be empty")
  private String description;

  private String notes;
  private List<String> categoryList;
  private TaskStatus status;
  private TaskPriority priority;
  private DateTime dueDate;
  private DateTime reminderDate;
}
