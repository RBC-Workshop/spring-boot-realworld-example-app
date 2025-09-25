package io.spring.api;

import io.spring.application.Page;
import io.spring.application.TaskQueryService;
import io.spring.application.task.NewTaskParam;
import io.spring.application.task.TaskCommandService;
import io.spring.application.task.UpdateTaskParam;
import io.spring.core.task.Task;
import io.spring.core.task.TaskPriority;
import io.spring.core.task.TaskStatus;
import io.spring.core.user.User;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/tasks")
@AllArgsConstructor
public class TasksApi {
  private TaskCommandService taskCommandService;
  private TaskQueryService taskQueryService;

  @PostMapping
  public ResponseEntity createTask(
      @Valid @RequestBody NewTaskParam newTaskParam, @AuthenticationPrincipal User user) {
    Task task = taskCommandService.createTask(newTaskParam, user);
    return ResponseEntity.ok(
        new HashMap<String, Object>() {
          {
            put("task", taskQueryService.findById(task.getId(), user).get());
          }
        });
  }

  @GetMapping
  public ResponseEntity getTasks(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "20") int limit,
      @RequestParam(value = "status", required = false) TaskStatus status,
      @RequestParam(value = "priority", required = false) TaskPriority priority,
      @RequestParam(value = "category", required = false) String category,
      @RequestParam(value = "dueBefore", required = false) String dueBefore,
      @AuthenticationPrincipal User user) {
    return ResponseEntity.ok(
        taskQueryService.findUserTasks(status, priority, category, dueBefore, new Page(offset, limit), user));
  }

  @GetMapping(path = "/overdue")
  public ResponseEntity getOverdueTasks(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok(taskQueryService.findOverdueTasks(user));
  }

  @GetMapping(path = "/today")
  public ResponseEntity getTodayTasks(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok(taskQueryService.findTodayTasks(user));
  }

  @GetMapping(path = "/{taskId}")
  public ResponseEntity getTask(@PathVariable("taskId") String taskId, @AuthenticationPrincipal User user) {
    return taskQueryService
        .findById(taskId, user)
        .map(taskData -> ResponseEntity.ok(Map.of("task", taskData)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping(path = "/{taskId}")
  public ResponseEntity updateTask(
      @PathVariable("taskId") String taskId,
      @Valid @RequestBody UpdateTaskParam updateTaskParam,
      @AuthenticationPrincipal User user) {
    return taskCommandService
        .updateTask(taskId, updateTaskParam, user)
        .map(
            task ->
                ResponseEntity.ok(
                    Map.of("task", taskQueryService.findById(task.getId(), user).get())))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping(path = "/{taskId}")
  public ResponseEntity deleteTask(
      @PathVariable("taskId") String taskId, @AuthenticationPrincipal User user) {
    boolean result = taskCommandService.deleteTask(taskId, user);
    return result ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
}
