package io.spring.core.task;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "name")
public class Category {
  private String id;
  private String name;

  public Category(String name) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
  }
}
