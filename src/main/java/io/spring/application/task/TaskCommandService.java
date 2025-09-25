package io.spring.application.task;

import io.spring.core.task.Task;
import io.spring.core.task.TaskRepository;
import io.spring.core.user.User;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TaskCommandService {
  private TaskRepository taskRepository;

  @Transactional
  public Task createTask(NewTaskParam newTaskParam, User user) {
    Task task =
        new Task(
            newTaskParam.getTitle(),
            newTaskParam.getDescription(),
            newTaskParam.getNotes(),
            newTaskParam.getCategoryList(),
            newTaskParam.getStatus(),
            newTaskParam.getPriority(),
            newTaskParam.getDueDate(),
            newTaskParam.getReminderDate(),
            user.getId());
    taskRepository.save(task);
    return task;
  }

  @Transactional
  public Optional<Task> updateTask(
      String taskId, UpdateTaskParam updateTaskParam, User user) {
    Optional<Task> optional = taskRepository.findById(taskId);
    if (optional.isPresent()) {
      Task task = optional.get();
      if (!task.getUserId().equals(user.getId())) {
        return Optional.empty();
      }
      task.update(
          updateTaskParam.getTitle(),
          updateTaskParam.getDescription(),
          updateTaskParam.getNotes(),
          updateTaskParam.getStatus(),
          updateTaskParam.getPriority(),
          updateTaskParam.getDueDate(),
          updateTaskParam.getReminderDate());
      taskRepository.save(task);
      return Optional.of(task);
    }
    return Optional.empty();
  }

  @Transactional
  public boolean deleteTask(String taskId, User user) {
    Optional<Task> optional = taskRepository.findById(taskId);
    if (optional.isPresent()) {
      Task task = optional.get();
      if (!task.getUserId().equals(user.getId())) {
        return false;
      }
      taskRepository.remove(task);
      return true;
    }
    return false;
  }
}
