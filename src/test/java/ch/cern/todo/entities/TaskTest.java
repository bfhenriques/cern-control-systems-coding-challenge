package ch.cern.todo.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private static final String taskName = "dummy task name";
    private static final String taskDescription = "dummy task description";
    private static final int categoryId = 1;

    @Test
    void testCorrectCreationOfTaskWithParamsConstructor() {
        Task task = new Task(taskName, taskDescription, categoryId);
        executeAssertions(task);
    }

    @Test
    void testCorrectCreationOfTaskWithNoParamsConstructor() {
        Task task = new Task();
        task.setTaskName(taskName);
        task.setTaskDescription(taskDescription);
        task.setCategoryId(categoryId);
        executeAssertions(task);
    }

    private static void executeAssertions(Task task) {
        assertEquals(0, task.getTaskId());
        assertEquals(taskName, task.getTaskName());
        assertEquals(taskDescription, task.getTaskDescription());
        assertEquals(categoryId, task.getCategoryId());
    }
}