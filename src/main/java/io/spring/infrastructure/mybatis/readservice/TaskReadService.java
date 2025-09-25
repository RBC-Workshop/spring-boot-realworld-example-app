package io.spring.infrastructure.mybatis.readservice;

import io.spring.application.Page;
import io.spring.application.data.TaskData;
import io.spring.core.task.TaskPriority;
import io.spring.core.task.TaskStatus;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;

@Mapper
public interface TaskReadService {
  TaskData findById(@Param("id") String id);

  List<String> queryTasks(
      @Param("status") TaskStatus status,
      @Param("priority") TaskPriority priority,
      @Param("category") String category,
      @Param("dueBefore") String dueBefore,
      @Param("userId") String userId,
      @Param("page") Page page);

  int countTask(
      @Param("status") TaskStatus status,
      @Param("priority") TaskPriority priority,
      @Param("category") String category,
      @Param("dueBefore") String dueBefore,
      @Param("userId") String userId);

  List<TaskData> findTasks(@Param("ids") List<String> ids);

  List<String> findOverdueTasks(@Param("now") DateTime now, @Param("userId") String userId);

  List<String> findTasksDueInRange(
      @Param("start") DateTime start, @Param("end") DateTime end, @Param("userId") String userId);
}
