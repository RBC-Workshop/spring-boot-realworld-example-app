package io.spring.infrastructure.mybatis.readservice;

import io.spring.application.data.TaskNoteData;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskNoteReadService {
  TaskNoteData findById(@Param("id") String id);

  List<TaskNoteData> findByTaskId(@Param("taskId") String taskId);
}
