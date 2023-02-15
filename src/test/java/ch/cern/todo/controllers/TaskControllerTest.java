package ch.cern.todo.controllers;

import ch.cern.todo.Utils;
import ch.cern.todo.entities.Task;
import ch.cern.todo.repositories.CategoryRepository;
import ch.cern.todo.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    TaskRepository taskRepository;

    Utils utils = new Utils();

    @Test
    void shouldCreateCategory() throws Exception {
        String uri = "/add-task";
        Task task = new Task("dummy name", "dummy description", Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)), 1);

        MvcResult mvcResult = mvc.perform(
                        post(uri)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(utils.mapToJson(task)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    void shouldGetTasks() throws Exception {
        String uri = "/all-tasks";

        Task task1 = new Task("dummy name 1", "dummy description 1", Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)), 1);
        Task task2 = new Task("dummy name 2", "dummy description 2", Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)), 1);
        List<Task> listTasks = new ArrayList<>(Arrays.asList(task1, task2));

        when(taskRepository.findAll()).thenReturn(listTasks);
        MvcResult mvcResult = mvc.perform(get(uri))
                .andExpect(jsonPath("$.size()").value(listTasks.size()))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void shouldNotGetTasks() throws Exception {
        String uri = "/all-tasks";

        when(taskRepository.findAll()).thenReturn(List.of());
        MvcResult mvcResult = mvc.perform(get(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
    }

    @Test
    void shouldGetTaskById() throws Exception {
        int taskId = 0;
        String uri = "/tasks/" + taskId;
        String taskName = "dummy name 1";
        String taskDescription = "dummy description 1";
        Timestamp deadline = Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS));
        int categoryId = 1;
        Task task1 = new Task(taskName, taskDescription, deadline, categoryId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task1));
        MvcResult mvcResult = mvc.perform(get(uri))
                .andExpect(jsonPath("$.taskId").value(taskId))
                .andExpect(jsonPath("$.taskName").value(taskName))
                .andExpect(jsonPath("$.taskDescription").value(taskDescription))
                .andExpect(jsonPath("$.deadline").value(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(deadline)))
                .andExpect(jsonPath("$.categoryId").value(categoryId))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void shouldNotFindTaskById() throws Exception {
        int taskId = 0;
        String uri = "/tasks/" + taskId;

        when(categoryRepository.findById(taskId)).thenReturn(Optional.empty());
        MvcResult mvcResult = mvc.perform(get(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void shouldGetTasksByCategoryId() throws Exception {
        int categoryId = 1;
        String uri = "/category/" + categoryId + "/all-tasks";
        Task task1 = new Task("dummy name 1", "dummy description 1", Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)), 1);
        Task task2 = new Task("dummy name 2", "dummy description 2", Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)), 1);
        List<Task> listTasks = new ArrayList<>(Arrays.asList(task1, task2));

        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(taskRepository.findAll()).thenReturn(listTasks);
        MvcResult mvcResult = mvc.perform(get(uri))
                .andExpect(jsonPath("$.size()").value(listTasks.size()))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void shouldEditTask() throws Exception {
        int taskId = 0;
        String uri = "/tasks/" + taskId;
        Timestamp deadline = Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS));
        Task task1 = new Task("dummy name 1", "dummy description 1", deadline, 1);
        Task task2 = new Task("dummy name 2", "dummy description 2", deadline, 2);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenReturn(task2);
        MvcResult mvcResult = mvc.perform(put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(utils.mapToJson(task2)))
                .andExpect(jsonPath("$.taskName").value(task2.getTaskName()))
                .andExpect(jsonPath("$.taskDescription").value(task2.getTaskDescription()))
                .andExpect(jsonPath("$.deadline").value(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(deadline)))
                .andExpect(jsonPath("$.categoryId").value(task2.getCategoryId()))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void shouldNotFindTaskToEdit() throws Exception {
        int taskId = 0;
        String uri = "/tasks/" + taskId;
        Task task2 = new Task("dummy name 2", "dummy description 2", Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)), 1);

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
        when(taskRepository.save(any(Task.class))).thenReturn(task2);
        MvcResult mvcResult = mvc.perform(put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(utils.mapToJson(task2)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void shouldDeleteTask() throws Exception {
        int taskId = 0;
        String uri = "/tasks/" + taskId;

        doNothing().when(taskRepository).deleteById(0);
        MvcResult mvcResult = mvc.perform(delete(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void shouldDeleteTasks() throws Exception {
        String uri = "/tasks";

        doNothing().when(taskRepository).deleteAll();
        MvcResult mvcResult = mvc.perform(delete(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void shouldDeleteTasksByCategoryId() throws Exception {
        int categoryId = 1;
        String uri = "/category/1/all-tasks";
        Task task1 = new Task("dummy name 1", "dummy description 1", Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)), 1);
        Task task2 = new Task("dummy name 2", "dummy description 2", Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)), 1);
        List<Task> listTasks = new ArrayList<>(Arrays.asList(task1, task2));

        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(taskRepository.findAll()).thenReturn(listTasks);
        doNothing().when(taskRepository).deleteById(categoryId);
        MvcResult mvcResult = mvc.perform(delete(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}