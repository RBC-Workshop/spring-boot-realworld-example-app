package io.spring.infrastructure.repository;

import io.spring.core.task.Task;
import io.spring.core.task.TaskRepository;
import io.spring.infrastructure.mybatis.mapper.TaskMapper;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MyBatisTaskRepository implements TaskRepository {
  private TaskMapper taskMapper;

  public MyBatisTaskRepository(TaskMapper taskMapper) {
    this.taskMapper = taskMapper;
  }

  @Override
  public void save(Task task) {
    if (taskMapper.findById(task.getId()) == null) {
      taskMapper.insert(task);
    } else {
      taskMapper.update(task);
    }
  }

  @Override
  public Optional<Task> findById(String id) {
    return Optional.ofNullable(taskMapper.findById(id));
  }

  @Override
  public Optional<Task> findByTitle(String title) {
    return Optional.ofNullable(taskMapper.findByTitle(title));
  }

  @Override
  public void remove(Task task) {
    taskMapper.remove(task.getId());
  }
}
