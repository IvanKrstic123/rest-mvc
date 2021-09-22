package guru.springframework.restmvc.services;

import guru.springframework.restmvc.api.v1.mapper.CategoryMapper;
import guru.springframework.restmvc.api.v1.model.CategoryDTO;
import guru.springframework.restmvc.domain.Category;
import guru.springframework.restmvc.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    public static final long ID = 3L;
    public static final String SWEETS = "Sweets";

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
    }

    @Test
    void getAllCategories() {

        //given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> allCategories = categoryService.getAllCategories();

        //then
        assertEquals(3, allCategories.size());
        verify(categoryRepository, times(1)).findAll();

    }

    @Test
    void getCategoryByName() {

        //given
        Category categoryByName = new Category();
        categoryByName.setId(ID);
        categoryByName.setName(SWEETS);

        when(categoryRepository.findByName(anyString())).thenReturn(categoryByName);

        //when
        CategoryDTO categoryDTO = categoryService.getCategoryByName(SWEETS);

        //then
        assertNotNull(categoryDTO);
        assertEquals(ID, categoryDTO.getId());
        assertEquals(SWEETS, categoryDTO.getName());

        verify(categoryRepository, times(1)).findByName(anyString());
    }
}