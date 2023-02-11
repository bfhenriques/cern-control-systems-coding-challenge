package ch.cern.todo.services;

import ch.cern.todo.entities.Task;
import ch.cern.todo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements IRepositoryService<Task> {

    @Autowired
    private TaskRepository repo;

    @Override
    public Task create(Task task) {
        return repo.save(task);
    }

    @Override
    public List<Task> getAll() {
        return repo.findAll();
    }

    @Override
    public Task getById(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Task edit(Task task) {
        Task persistedTask = repo.findById(task.getTaskId()).orElse(null);

        if (persistedTask != null) {
            persistedTask.setTaskName(task.getTaskName());
            persistedTask.setTaskDescription(task.getTaskDescription());
            persistedTask.setCategoryId(task.getCategoryId());

            return repo.save(persistedTask);
        }

        return repo.save(task);
    }

    @Override
    public Boolean delete(int id) {
        repo.deleteById(id);
        return true;
    }

    /*
    public Task createTask(Task task) {
        return repo.save(task);
    }

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public Task getTaskById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Task editTask(Task task) {
        Task persistedTask = repo.findById(task.getTaskId()).orElse(null);

        if (persistedTask != null) {
            persistedTask.setTaskName(task.getTaskName());
            persistedTask.setTaskDescription(task.getTaskDescription());
            persistedTask.setCategoryId(task.getCategoryId());

            return repo.save(persistedTask);
        }

        return repo.save(task);
    }

    public Boolean deleteTask(int id) {
        repo.deleteById(id);
        return true;
    }
    */
}
