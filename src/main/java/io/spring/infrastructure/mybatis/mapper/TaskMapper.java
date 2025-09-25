package io.spring.infrastructure.mybatis.mapper;

import io.spring.core.task.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskMapper {
  void insert(@Param("task") Task task);

  Task findById(@Param("id") String id);

  Task findByTitle(@Param("title") String title);

  void update(@Param("task") Task task);

  void remove(@Param("id") String id);
}
