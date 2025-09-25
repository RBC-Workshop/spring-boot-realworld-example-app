package io.spring.api;

import io.spring.application.TaskQueryService;
import io.spring.application.task.TaskImportantCommandService;
import io.spring.core.user.User;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/tasks/{taskId}/important")
@AllArgsConstructor
public class TaskImportantApi {
  private TaskImportantCommandService taskImportantCommandService;
  private TaskQueryService taskQueryService;

  @PostMapping
  public ResponseEntity markTaskAsImportant(
      @PathVariable("taskId") String taskId, @AuthenticationPrincipal User user) {
    return taskImportantCommandService
        .markAsImportant(taskId, user)
        .map(
            task ->
                ResponseEntity.ok(
                    new HashMap<String, Object>() {
                      {
                        put("task", taskQueryService.findById(task.getId(), user).get());
                      }
                    }))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping
  public ResponseEntity unmarkTaskAsImportant(
      @PathVariable("taskId") String taskId, @AuthenticationPrincipal User user) {
    return taskImportantCommandService
        .unmarkAsImportant(taskId, user)
        .map(
            task ->
                ResponseEntity.ok(
                    new HashMap<String, Object>() {
                      {
                        put("task", taskQueryService.findById(task.getId(), user).get());
                      }
                    }))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
