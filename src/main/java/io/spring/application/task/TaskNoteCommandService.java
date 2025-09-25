package io.spring.application.task;

import io.spring.api.NewTaskNoteParam;
import io.spring.core.task.Task;
import io.spring.core.task.TaskNote;
import io.spring.core.task.TaskNoteRepository;
import io.spring.core.task.TaskRepository;
import io.spring.core.user.User;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TaskNoteCommandService {
  private TaskRepository taskRepository;
  private TaskNoteRepository taskNoteRepository;

  @Transactional
  public Optional<TaskNote> createTaskNote(
      NewTaskNoteParam newTaskNoteParam, User user, String taskId) {
    Optional<Task> optional = taskRepository.findById(taskId);
    return optional.map(
        task -> {
          TaskNote taskNote = new TaskNote(newTaskNoteParam.getBody(), user.getId(), task.getId());
          taskNoteRepository.save(taskNote);
          return taskNote;
        });
  }

  @Transactional
  public boolean deleteTaskNote(String id, User user) {
    Optional<TaskNote> optional = taskNoteRepository.findById(id);
    return optional
        .map(
            taskNote -> {
              if (taskNote.getUserId().equals(user.getId())) {
                taskNoteRepository.remove(taskNote);
                return true;
              } else {
                return false;
              }
            })
        .orElse(false);
  }
}
