package guru.springframework.restmvc.services;

import java.util.List;
import guru.springframework.restmvc.api.v1.model.CategoryDTO;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
