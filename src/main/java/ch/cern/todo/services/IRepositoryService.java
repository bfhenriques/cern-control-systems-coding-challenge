package ch.cern.todo.services;

import java.util.List;

public interface IRepositoryService<T> {

    T create(T entity);

    List<T> getAll();

    T getById(int id);

    T edit(T entity);

    Boolean delete(int id);
}
