package ch.cern.todo.controllers;

import ch.cern.todo.entities.Task;
import ch.cern.todo.repositories.CategoryRepository;
import ch.cern.todo.repositories.TaskRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "https://localhost:8080")
@RestController
public class TaskController {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TaskRepository taskRepository;

    @PostMapping("/add-task")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        try {
            logger.info(String.format("Creating task: %s.", task));
            Task persistedTask = taskRepository.save(task);

            logger.info(String.format("Created task: %s.", persistedTask));
            return new ResponseEntity<>(persistedTask, HttpStatus.CREATED);
        } catch (Exception ex) {
            logger.error("Error creating task.", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            logger.info("Getting all categories.");
            List<Task> allTasks = taskRepository.findAll();

            if (allTasks.isEmpty()) {
                logger.info("No tasks found.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            logger.info(String.format("All tasks: %s.", allTasks));
            return new ResponseEntity<>(allTasks, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting all tasks.", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") int taskId) {
        try {
            logger.info(String.format("Getting task with id=%s.", taskId));
            Optional<Task> persistedTask = taskRepository.findById(taskId);

            if (persistedTask.isEmpty()) {
                logger.info(String.format("Task with id=%s not found.", taskId));
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            logger.info(String.format("Task: %s.", persistedTask));
            return new ResponseEntity<>(persistedTask.get(), HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(String.format("Error getting task with id=%s.", taskId), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{id}/all-tasks")
    public ResponseEntity<List<Task>> getTasksByCategoryId(@PathVariable("id") int categoryId) {
        try {
            logger.info(String.format("Getting tasks with categoryId=%s.", categoryId));

            if (!categoryRepository.existsById(categoryId)) {
                logger.info(String.format("Category with id=%s not found.", categoryId));
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            List<Task> tasks = taskRepository.findAll().stream()
                    .filter(task -> task.getCategoryId() == categoryId)
                    .collect(Collectors.toList());

            logger.info(String.format("Tasks: %s.", tasks));
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(String.format("Error getting tasks with categoryId=%s.", categoryId), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("tasks/{id}")
    public ResponseEntity<Task> editTask(@PathVariable("id") int taskId, @RequestBody Task task) {
        try {
            logger.info(String.format("Editing task with id=%s.", taskId));
            Optional<Task> persistedTask = taskRepository.findById(taskId);

            if (persistedTask.isEmpty()) {
                logger.info(String.format("Task with id=%s not found.", taskId));
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            Task editTask = persistedTask.get();
            editTask.setTaskName(task.getTaskName());
            editTask.setTaskDescription(task.getTaskDescription());
            editTask.setDeadline(task.getDeadline());
            editTask.setCategoryId(task.getCategoryId());

            logger.info(String.format("Edited task: %s.", persistedTask));
            return new ResponseEntity<>(taskRepository.save(editTask), HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(String.format("Error editing task with id=%s.", taskId), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("tasks/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") int taskId) {
        try {
            logger.info(String.format("Deleting task with id=%s.", taskId));
            taskRepository.deleteById(taskId);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(String.format("Error deleting task with %s.", taskId), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("tasks")
    public ResponseEntity<Task> deleteAllTasks() {
        try {
            logger.info("Deleting all tasks.");
            taskRepository.deleteAll();

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error deleting all tasks.", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("category/{id}/all-tasks")
    public ResponseEntity<Task> deleteByCategoryId(@PathVariable("id") int categoryId) {
        try {
            logger.info(String.format("Deleting all tasks with categoryId=%s.", categoryId));

            if (!categoryRepository.existsById(categoryId)) {
                logger.info(String.format("Category with id=%s not found.", categoryId));
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            List<Integer> allTaskIds = taskRepository.findAll().stream()
                .filter(task -> task.getCategoryId() == categoryId)
                .map(Task::getTaskId)
                .collect(Collectors.toList());

            allTaskIds.forEach(taskId -> taskRepository.deleteById(taskId));

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error deleting all tasks.", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
