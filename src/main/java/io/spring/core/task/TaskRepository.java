package io.spring.core.task;

import java.util.Optional;

public interface TaskRepository {
  void save(Task task);

  Optional<Task> findById(String id);

  Optional<Task> findByTitle(String title);

  void remove(Task task);
}
