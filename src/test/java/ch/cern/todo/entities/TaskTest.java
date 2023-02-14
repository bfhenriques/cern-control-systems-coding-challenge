package ch.cern.todo.entities;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private static final String taskName = "dummy task name";
    private static final String taskDescription = "dummy task description";
    private static final Timestamp deadline = Timestamp.from(Instant.now().plus(1,ChronoUnit.DAYS));
    private static final int categoryId = 1;

    @Test
    void testCorrectCreationOfTaskWithParamsConstructor() {
        Task task = new Task(taskName, taskDescription, deadline, categoryId);
        executeAssertions(task);
    }

    @Test
    void testCorrectCreationOfTaskWithNoParamsConstructor() {
        Task task = new Task();
        task.setTaskName(taskName);
        task.setTaskDescription(taskDescription);
        task.setDeadline(deadline);
        task.setCategoryId(categoryId);
        executeAssertions(task);
    }

    private static void executeAssertions(Task task) {
        assertEquals(0, task.getTaskId());
        assertEquals(taskName, task.getTaskName());
        assertEquals(taskDescription, task.getTaskDescription());
        assertEquals(deadline, task.getDeadline());
        assertEquals(categoryId, task.getCategoryId());
    }
}