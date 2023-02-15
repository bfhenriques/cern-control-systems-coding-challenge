package ch.cern.todo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TaskId")
    private int taskId;

    @Column(name = "TaskName", length = 100)
    private String taskName;

    @Column(name = "TaskDescription", length = 500)
    private String taskDescription;

    @Column(name = "Deadline")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp deadline;

    @Column(name = "CategoryId")
    private int categoryId;

    public Task(String taskName, String taskDescription, Timestamp deadline, int categoryId) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.deadline = deadline;
        this.categoryId = categoryId;
    }

    public Task(String taskName, String taskDescription, Timestamp deadline) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.deadline = deadline;
    }

    public Task() {}

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return String.format("Task(id=%s, name=\"%s\", description=\"%s\", deadline=\"%s\", categoryId=\"%s\")", taskId, taskName, taskDescription, deadline, categoryId);
    }
}
