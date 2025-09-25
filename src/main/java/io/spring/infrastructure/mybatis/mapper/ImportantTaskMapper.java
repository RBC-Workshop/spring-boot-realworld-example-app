package io.spring.infrastructure.mybatis.mapper;

import io.spring.core.task.ImportantTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ImportantTaskMapper {
  void insert(@Param("importantTask") ImportantTask importantTask);

  ImportantTask find(@Param("taskId") String taskId, @Param("userId") String userId);

  void remove(@Param("importantTask") ImportantTask importantTask);
}
