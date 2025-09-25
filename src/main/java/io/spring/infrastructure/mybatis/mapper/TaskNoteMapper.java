package io.spring.infrastructure.mybatis.mapper;

import io.spring.core.task.TaskNote;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskNoteMapper {
  void insert(@Param("taskNote") TaskNote taskNote);

  TaskNote findById(@Param("id") String id);

  void remove(@Param("id") String id);
}
