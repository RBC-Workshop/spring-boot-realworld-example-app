package io.spring.application.task;

import io.spring.core.task.ImportantTask;
import io.spring.core.task.ImportantTaskRepository;
import io.spring.core.task.Task;
import io.spring.core.task.TaskRepository;
import io.spring.core.user.User;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TaskImportantCommandService {
  private TaskRepository taskRepository;
  private ImportantTaskRepository importantTaskRepository;

  @Transactional
  public Optional<Task> markAsImportant(String taskId, User user) {
    Optional<Task> optional = taskRepository.findById(taskId);
    if (optional.isPresent()) {
      Task task = optional.get();
      if (!importantTaskRepository.find(task.getId(), user.getId()).isPresent()) {
        ImportantTask importantTask = new ImportantTask(task.getId(), user.getId());
        importantTaskRepository.save(importantTask);
      }
      return Optional.of(task);
    } else {
      return Optional.empty();
    }
  }

  @Transactional
  public Optional<Task> unmarkAsImportant(String taskId, User user) {
    Optional<Task> optional = taskRepository.findById(taskId);
    if (optional.isPresent()) {
      Task task = optional.get();
      Optional<ImportantTask> importantTaskOptional =
          importantTaskRepository.find(task.getId(), user.getId());
      importantTaskOptional.ifPresent(importantTaskRepository::remove);
      return Optional.of(task);
    } else {
      return Optional.empty();
    }
  }
}
