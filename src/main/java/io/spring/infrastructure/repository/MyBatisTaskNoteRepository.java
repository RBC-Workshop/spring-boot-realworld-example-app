package io.spring.infrastructure.repository;

import io.spring.core.task.TaskNote;
import io.spring.core.task.TaskNoteRepository;
import io.spring.infrastructure.mybatis.mapper.TaskNoteMapper;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MyBatisTaskNoteRepository implements TaskNoteRepository {
  private TaskNoteMapper taskNoteMapper;

  public MyBatisTaskNoteRepository(TaskNoteMapper taskNoteMapper) {
    this.taskNoteMapper = taskNoteMapper;
  }

  @Override
  public void save(TaskNote taskNote) {
    taskNoteMapper.insert(taskNote);
  }

  @Override
  public Optional<TaskNote> findById(String id) {
    return Optional.ofNullable(taskNoteMapper.findById(id));
  }

  @Override
  public void remove(TaskNote taskNote) {
    taskNoteMapper.remove(taskNote.getId());
  }
}
