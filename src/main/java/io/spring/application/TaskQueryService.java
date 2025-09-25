package io.spring.application;

import static java.util.stream.Collectors.toList;

import io.spring.application.Page;
import io.spring.application.data.TaskData;
import io.spring.application.data.TaskDataList;
import io.spring.application.data.TaskImportantCount;
import io.spring.core.task.TaskPriority;
import io.spring.core.task.TaskStatus;
import io.spring.core.user.User;
import io.spring.infrastructure.mybatis.readservice.TaskImportantReadService;
import io.spring.infrastructure.mybatis.readservice.TaskReadService;
import io.spring.infrastructure.mybatis.readservice.UserRelationshipQueryService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskQueryService {
  private TaskReadService taskReadService;
  private UserRelationshipQueryService userRelationshipQueryService;
  private TaskImportantReadService taskImportantReadService;

  public Optional<TaskData> findById(String id, User user) {
    TaskData taskData = taskReadService.findById(id);
    if (taskData == null) {
      return Optional.empty();
    } else {
      if (user != null) {
        fillExtraInfo(id, user, taskData);
      }
      return Optional.of(taskData);
    }
  }

  public TaskDataList findUserTasks(
      TaskStatus status, TaskPriority priority, String category, String dueBefore, Page page, User user) {
    List<String> taskIds = taskReadService.queryTasks(status, priority, category, dueBefore, user.getId(), page);
    int taskCount = taskReadService.countTask(status, priority, category, dueBefore, user.getId());
    if (taskIds.size() == 0) {
      return new TaskDataList(new ArrayList<>(), taskCount);
    } else {
      List<TaskData> tasks = taskReadService.findTasks(taskIds);
      fillExtraInfo(tasks, user);
      return new TaskDataList(tasks, taskCount);
    }
  }

  public TaskDataList findOverdueTasks(User user) {
    DateTime now = new DateTime();
    List<String> taskIds = taskReadService.findOverdueTasks(now, user.getId());
    if (taskIds.size() == 0) {
      return new TaskDataList(new ArrayList<>(), 0);
    } else {
      List<TaskData> tasks = taskReadService.findTasks(taskIds);
      fillExtraInfo(tasks, user);
      return new TaskDataList(tasks, tasks.size());
    }
  }

  public TaskDataList findTodayTasks(User user) {
    DateTime today = new DateTime().withTimeAtStartOfDay();
    DateTime tomorrow = today.plusDays(1);
    List<String> taskIds = taskReadService.findTasksDueInRange(today, tomorrow, user.getId());
    if (taskIds.size() == 0) {
      return new TaskDataList(new ArrayList<>(), 0);
    } else {
      List<TaskData> tasks = taskReadService.findTasks(taskIds);
      fillExtraInfo(tasks, user);
      return new TaskDataList(tasks, tasks.size());
    }
  }

  private void fillExtraInfo(List<TaskData> tasks, User currentUser) {
    setImportantCount(tasks);
    if (currentUser != null) {
      setIsImportant(tasks, currentUser);
    }
  }

  private void setImportantCount(List<TaskData> tasks) {
    List<TaskImportantCount> importantCounts =
        taskImportantReadService.tasksImportantCount(
            tasks.stream().map(TaskData::getId).collect(toList()));
    Map<String, Integer> countMap = new HashMap<>();
    importantCounts.forEach(
        item -> {
          countMap.put(item.getId(), item.getCount());
        });
    tasks.forEach(
        taskData -> taskData.setImportantCount(countMap.getOrDefault(taskData.getId(), 0)));
  }

  private void setIsImportant(List<TaskData> tasks, User currentUser) {
    Set<String> importantTasks =
        taskImportantReadService.userImportantTasks(
            tasks.stream().map(taskData -> taskData.getId()).collect(toList()),
            currentUser);

    tasks.forEach(
        taskData -> {
          if (importantTasks.contains(taskData.getId())) {
            taskData.setImportant(true);
          }
        });
  }

  private void fillExtraInfo(String id, User user, TaskData taskData) {
    taskData.setImportant(taskImportantReadService.isUserImportantTask(user.getId(), id));
    taskData.setImportantCount(taskImportantReadService.taskImportantCount(id));
  }
}
