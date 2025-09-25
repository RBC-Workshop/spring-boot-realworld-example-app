package io.spring.core.task;

import java.util.Optional;

public interface TaskNoteRepository {
  void save(TaskNote taskNote);

  Optional<TaskNote> findById(String id);

  void remove(TaskNote taskNote);
}
