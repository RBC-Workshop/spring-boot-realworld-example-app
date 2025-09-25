package io.spring.core.task;

import static java.util.stream.Collectors.toList;

import io.spring.Util;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Task {
  private String userId;
  private String id;
  private String title;
  private String description;
  private String notes;
  private List<Category> categories;
  private TaskStatus status;
  private TaskPriority priority;
  private DateTime dueDate;
  private DateTime reminderDate;
  private DateTime createdAt;
  private DateTime updatedAt;

  public Task(
      String title, String description, String notes, List<String> categoryList, 
      TaskStatus status, TaskPriority priority, DateTime dueDate, DateTime reminderDate, String userId) {
    this(title, description, notes, categoryList, status, priority, dueDate, reminderDate, userId, new DateTime());
  }

  public Task(
      String title,
      String description,
      String notes,
      List<String> categoryList,
      TaskStatus status,
      TaskPriority priority,
      DateTime dueDate,
      DateTime reminderDate,
      String userId,
      DateTime createdAt) {
    this.id = UUID.randomUUID().toString();
    this.title = title;
    this.description = description;
    this.notes = notes;
    this.categories = new HashSet<>(categoryList).stream().map(Category::new).collect(toList());
    this.status = status != null ? status : TaskStatus.TODO;
    this.priority = priority != null ? priority : TaskPriority.MEDIUM;
    this.dueDate = dueDate;
    this.reminderDate = reminderDate;
    this.userId = userId;
    this.createdAt = createdAt;
    this.updatedAt = createdAt;
  }

  public void update(String title, String description, String notes, TaskStatus status, 
                     TaskPriority priority, DateTime dueDate, DateTime reminderDate) {
    if (!Util.isEmpty(title)) {
      this.title = title;
      this.updatedAt = new DateTime();
    }
    if (!Util.isEmpty(description)) {
      this.description = description;
      this.updatedAt = new DateTime();
    }
    if (!Util.isEmpty(notes)) {
      this.notes = notes;
      this.updatedAt = new DateTime();
    }
    if (status != null) {
      this.status = status;
      this.updatedAt = new DateTime();
    }
    if (priority != null) {
      this.priority = priority;
      this.updatedAt = new DateTime();
    }
    if (dueDate != null) {
      this.dueDate = dueDate;
      this.updatedAt = new DateTime();
    }
    if (reminderDate != null) {
      this.reminderDate = reminderDate;
      this.updatedAt = new DateTime();
    }
  }
}
