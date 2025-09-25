package io.spring.infrastructure.mybatis.readservice;

import io.spring.application.data.TaskImportantCount;
import io.spring.core.user.User;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskImportantReadService {
  boolean isUserImportantTask(@Param("userId") String userId, @Param("taskId") String taskId);

  int taskImportantCount(@Param("taskId") String taskId);

  List<TaskImportantCount> tasksImportantCount(@Param("taskIds") List<String> taskIds);

  Set<String> userImportantTasks(
      @Param("taskIds") List<String> taskIds, @Param("currentUser") User currentUser);
}
