package io.spring.core.task;

import java.util.Optional;

public interface ImportantTaskRepository {
  void save(ImportantTask importantTask);

  Optional<ImportantTask> find(String taskId, String userId);

  void remove(ImportantTask importantTask);
}
