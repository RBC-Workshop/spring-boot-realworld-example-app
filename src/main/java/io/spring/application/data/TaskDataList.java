package io.spring.application.data;

import java.util.List;
import lombok.Getter;

@Getter
public class TaskDataList {
  private final List<TaskData> tasks;
  private final int tasksCount;

  public TaskDataList(List<TaskData> tasks, int tasksCount) {
    this.tasks = tasks;
    this.tasksCount = tasksCount;
  }
}
