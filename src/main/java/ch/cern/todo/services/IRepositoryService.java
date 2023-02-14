package ch.cern.todo.services;

import java.util.List;
import java.util.Optional;

public interface IRepositoryService<T> {

    T create(T entity);

    List<T> getAll();

    Optional<T> getById(int id);

    Optional<T> edit(int id, T entity);

    void delete(int id);
}
