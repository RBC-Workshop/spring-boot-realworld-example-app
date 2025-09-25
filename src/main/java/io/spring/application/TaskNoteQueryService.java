package io.spring.application;

import io.spring.application.data.TaskNoteData;
import io.spring.core.user.User;
import io.spring.infrastructure.mybatis.readservice.TaskNoteReadService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskNoteQueryService {
  private TaskNoteReadService taskNoteReadService;

  public TaskNoteData findById(String id, User user) {
    TaskNoteData taskNoteData = taskNoteReadService.findById(id);
    if (taskNoteData != null) {
      taskNoteData.setMine(taskNoteData.getUserId().equals(user.getId()));
    }
    return taskNoteData;
  }

  public List<TaskNoteData> findByTaskId(String taskId) {
    return taskNoteReadService.findByTaskId(taskId);
  }
}
