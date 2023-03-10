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
    void testCorrectCreationOfTaskWithFullParamsConstructor() {
        Task task = new Task(taskName, taskDescription, deadline, categoryId);
        executeAssertions(task, false);
    }

    @Test
    void testCorrectCreationOfTaskWithPartialParamsConstructor() {
        Task task = new Task(taskName, taskDescription, deadline);
        executeAssertions(task, true);
    }

    @Test
    void testCorrectCreationOfTaskWithNoParamsConstructor() {
        Task task = new Task();
        task.setTaskName(taskName);
        task.setTaskDescription(taskDescription);
        task.setDeadline(deadline);
        task.setCategoryId(categoryId);
        executeAssertions(task, false);
    }

    private static void executeAssertions(Task task, boolean skipCategory) {
        assertEquals(0, task.getTaskId());
        assertEquals(taskName, task.getTaskName());
        assertEquals(taskDescription, task.getTaskDescription());
        assertEquals(deadline, task.getDeadline());
        if (!skipCategory) assertEquals(categoryId, task.getCategoryId());
    }
}