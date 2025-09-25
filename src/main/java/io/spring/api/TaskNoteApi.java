package io.spring.api;

import io.spring.application.TaskNoteQueryService;
import io.spring.application.task.TaskNoteCommandService;
import io.spring.core.task.TaskNote;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/tasks/{taskId}/notes")
@AllArgsConstructor
public class TaskNoteApi {
  private TaskNoteCommandService taskNoteCommandService;
  private TaskNoteQueryService taskNoteQueryService;

  @PostMapping
  public ResponseEntity createTaskNote(
      @PathVariable("taskId") String taskId,
      @Valid @RequestBody NewTaskNoteParam newTaskNoteParam,
      @AuthenticationPrincipal User user) {
    return taskNoteCommandService
        .createTaskNote(newTaskNoteParam, user, taskId)
        .map(
            taskNote ->
                ResponseEntity.ok(
                    new HashMap<String, Object>() {
                      {
                        put("taskNote", taskNoteQueryService.findById(taskNote.getId(), user));
                      }
                    }))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity getTaskNotes(
      @PathVariable("taskId") String taskId, @AuthenticationPrincipal User user) {
    return ResponseEntity.ok(
        new HashMap<String, Object>() {
          {
            put("taskNotes", taskNoteQueryService.findByTaskId(taskId));
          }
        });
  }

  @DeleteMapping(path = "{id}")
  public ResponseEntity deleteTaskNote(
      @PathVariable("taskId") String taskId,
      @PathVariable("id") String id,
      @AuthenticationPrincipal User user) {
    boolean deleted = taskNoteCommandService.deleteTaskNote(id, user);
    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
}
