package ch.cern.todo.services;

import ch.cern.todo.entities.Task;
import ch.cern.todo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements IRepositoryService<Task> {

    @Autowired
    TaskRepository repo;

    @Override
    public Task create(Task task) {
        return repo.save(task);
    }

    @Override
    public List<Task> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Task> getById(int id) {
        return repo.findById(id);
    }

    @Override
    public Optional<Task> edit(int id, Task task) {
        Optional<Task> persistedTask = repo.findById(id);

        if (persistedTask.isPresent()) {
            Task optTask = persistedTask.get();
            optTask.setTaskName(task.getTaskName());
            optTask.setTaskDescription(task.getTaskDescription());
            optTask.setDeadline(task.getDeadline());
            optTask.setCategoryId(task.getCategoryId());

            return Optional.of(repo.save(optTask));
        }

        return Optional.empty();
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }

    public void deleteAll() {
        repo.deleteAll();
    }
}
