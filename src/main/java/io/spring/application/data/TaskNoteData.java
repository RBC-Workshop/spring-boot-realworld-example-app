package io.spring.application.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskNoteData {
  private String id;
  private String body;
  private String userId;
  private String taskId;
  private DateTime createdAt;
  private DateTime updatedAt;
  private ProfileData profileData;
  private boolean mine;
}
