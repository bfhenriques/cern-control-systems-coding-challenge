package ch.cern.todo.controllers;

import ch.cern.todo.Utils;
import ch.cern.todo.entities.Category;
import ch.cern.todo.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CategoryRepository categoryRepository;

    Utils utils = new Utils();

    @Test
    void shouldCreateCategory() throws Exception {
        String uri = "/add-category";
        Category category = new Category("dummy name", "dummy description");

        MvcResult mvcResult = mvc.perform(
                post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(utils.mapToJson(category)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    void shouldGetCategories() throws Exception {
        String uri = "/all-categories";

        Category category1 = new Category("dummy name 1", "dummy description 1");
        Category category2 = new Category("dummy name 2", "dummy description 2");
        List<Category> listCategories = new ArrayList<>(Arrays.asList(category1, category2));

        when(categoryRepository.findAll()).thenReturn(listCategories);
        MvcResult mvcResult = mvc.perform(get(uri))
                .andExpect(jsonPath("$.size()").value(listCategories.size()))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void shouldNotGetCategories() throws Exception {
        String uri = "/all-categories";

        when(categoryRepository.findAll()).thenReturn(List.of());
        MvcResult mvcResult = mvc.perform(get(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
    }

    @Test
    void shouldGetCategoryById() throws Exception {
        int categoryId = 0;
        String uri = "/categories/" + categoryId;
        String categoryName = "dummy name 1";
        String categoryDescription = "dummy description 1";
        Category category1 = new Category(categoryName, categoryDescription);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category1));
        MvcResult mvcResult = mvc.perform(get(uri))
                .andExpect(jsonPath("$.categoryId").value(categoryId))
                .andExpect(jsonPath("$.categoryName").value(categoryName))
                .andExpect(jsonPath("$.categoryDescription").value(categoryDescription))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void shouldNotFindCategoryById() throws Exception {
        int categoryId = 0;
        String uri = "/categories/" + categoryId;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        MvcResult mvcResult = mvc.perform(get(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void shouldEditCategory() throws Exception {
        int categoryId = 0;
        String uri = "/categories/" + categoryId;
        Category category1 = new Category("dummy name 1", "dummy description 1");
        Category category2 = new Category("dummy name 2", "dummy description 2");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category1));
        when(categoryRepository.save(any(Category.class))).thenReturn(category2);
        MvcResult mvcResult = mvc.perform(put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(utils.mapToJson(category2)))
                .andExpect(jsonPath("$.categoryName").value(category2.getCategoryName()))
                .andExpect(jsonPath("$.categoryDescription").value(category2.getCategoryDescription()))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void shouldNotFindCategoryToEdit() throws Exception {
        int categoryId = 0;
        String uri = "/categories/" + categoryId;
        Category category2 = new Category("dummy name 2", "dummy description 2");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(category2);
        MvcResult mvcResult = mvc.perform(put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(utils.mapToJson(category2)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        int categoryId = 0;
        String uri = "/categories/" + categoryId;

        doNothing().when(categoryRepository).deleteById(0);
        MvcResult mvcResult = mvc.perform(delete(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}