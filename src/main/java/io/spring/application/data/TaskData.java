package io.spring.application.data;

import io.spring.core.task.TaskPriority;
import io.spring.core.task.TaskStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskData {
  private String id;
  private String title;
  private String description;
  private String notes;
  private TaskStatus status;
  private TaskPriority priority;
  private DateTime dueDate;
  private DateTime reminderDate;
  private DateTime createdAt;
  private DateTime updatedAt;
  private ProfileData profileData;
  private List<String> categories;
  private boolean important;
  private int importantCount;
}
