package guru.springframework.restmvc.controllers.v1;

import java.util.List;

import guru.springframework.restmvc.api.v1.mapper.CategoryMapper;
import guru.springframework.restmvc.api.v1.model.CategoryDTO;
import guru.springframework.restmvc.controllers.RestResponseEntityExceptionHandler;
import guru.springframework.restmvc.services.CategoryService;
import guru.springframework.restmvc.services.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    public static final String FRUITS = "Fruits";
    public static final String JUICES = "Juices";

    @Mock
    CategoryMapper categoryMapper;

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(categoryController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void testListCategories() throws Exception {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(FRUITS);

        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setName(JUICES);

        List<CategoryDTO> categoryDTOList = Arrays.asList(categoryDTO, categoryDTO1);

        when(categoryService.getAllCategories()).thenReturn(categoryDTOList);

        mockMvc.perform(get(CategoryController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2))); // $ stands for root
    }

    @Test
    public void testGetCategoriesByName() throws Exception {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(FRUITS);

        when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);

        mockMvc.perform(get(CategoryController.BASE_URL + "Ivan")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(FRUITS)));
    }

    @Test
    void testGetByNameNotFound() throws Exception {

        when(categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CategoryController.BASE_URL + "/Fruits")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}