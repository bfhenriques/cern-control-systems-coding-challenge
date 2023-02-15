package ch.cern.todo.repositories;

import ch.cern.todo.entities.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    TaskRepository repo;

    @Test
    void shouldFindNoTasks() {
        List<Task> tasks = repo.findAll();

        assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldCreateTask() {
        Task task = repo.save(new Task("dummy name 1", "dummy description 1", Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)), 1));

        assertEquals("dummy name 1", task.getTaskName());
        assertEquals("dummy description 1", task.getTaskDescription());
    }

    @Test
    void shouldFindCategories() {
        Task task1 = new Task("dummy name 1", "dummy description 1", Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)), 1);
        Task task2 = new Task("dummy name 2", "dummy description 2", Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)), 1);

        entityManager.persist(task1);
        entityManager.persist(task2);

        List<Task> allTasks = repo.findAll();

        assertEquals(2, allTasks.size());
        entityManager.clear();
    }

    @Test
    void shouldFindCategoryById() {
        Task task1 = new Task("dummy name 1", "dummy description 1", Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)), 1);

        Task persistedTask = entityManager.persist(task1);

        Optional<Task> task = repo.findById(persistedTask.getTaskId());

        assertTrue(task.isPresent());
        assertEquals(task1.getTaskName(), task.get().getTaskName());
        assertEquals(task1.getTaskDescription(), task.get().getTaskDescription());
        assertEquals(task1.getCategoryId(), task.get().getCategoryId());
        entityManager.clear();
    }

    @Test
    void shouldDeleteCategoryById() {
        Task task1 = new Task("dummy name 1", "dummy description 1", Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)), 1);

        Task persistedTask = entityManager.persist(task1);
        int persistedId = persistedTask.getTaskId();
        repo.deleteById(persistedId);
        Optional<Task> task = repo.findById(persistedId);

        assertTrue(task.isEmpty());
        entityManager.clear();
    }

}