package io.spring.infrastructure.repository;

import io.spring.core.task.ImportantTask;
import io.spring.core.task.ImportantTaskRepository;
import io.spring.infrastructure.mybatis.mapper.ImportantTaskMapper;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MyBatisImportantTaskRepository implements ImportantTaskRepository {
  private ImportantTaskMapper importantTaskMapper;

  public MyBatisImportantTaskRepository(ImportantTaskMapper importantTaskMapper) {
    this.importantTaskMapper = importantTaskMapper;
  }

  @Override
  public void save(ImportantTask importantTask) {
    importantTaskMapper.insert(importantTask);
  }

  @Override
  public Optional<ImportantTask> find(String taskId, String userId) {
    return Optional.ofNullable(importantTaskMapper.find(taskId, userId));
  }

  @Override
  public void remove(ImportantTask importantTask) {
    importantTaskMapper.remove(importantTask);
  }
}
