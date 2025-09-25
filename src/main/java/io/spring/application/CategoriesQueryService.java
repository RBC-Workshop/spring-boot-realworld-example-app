package io.spring.application;

import io.spring.infrastructure.mybatis.readservice.CategoryReadService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoriesQueryService {
  private CategoryReadService categoryReadService;

  public List<String> allCategories() {
    return categoryReadService.findAll();
  }
}
