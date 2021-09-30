package guru.springframework.restmvc.api.v1.mapper;

import guru.springframework.restmvc.api.v1.model.CategoryDTO;
import guru.springframework.restmvc.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    public static final long ID = 1L;
    public static final String SWEETS = "Sweets";

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @BeforeEach
    void setUp() {
    }

    @Test
    void categoryToCategoryDTO() {

        //given
        Category category = new Category();
        category.setId(ID);
        category.setName(SWEETS);

        //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        //then
        assertNotNull(categoryDTO);
        assertEquals("Sweets", categoryDTO.getName());
    }
}