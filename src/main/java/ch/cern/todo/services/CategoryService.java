package ch.cern.todo.services;

import ch.cern.todo.entities.Category;
import ch.cern.todo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements IRepositoryService<Category> {

    @Autowired
    CategoryRepository repo;

    @Override
    public Category create(Category entity) {
        return repo.save(entity);
    }

    @Override
    public List<Category> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Category> getById(int id) {
        return repo.findById(id);
    }

    @Override
    public Optional<Category> edit(int id, Category category) {
        Optional<Category> persistedCategory = repo.findById(category.getCategoryId());

        if (persistedCategory.isPresent()) {
            Category optCategory = persistedCategory.get();
            optCategory.setCategoryName(category.getCategoryName());
            optCategory.setCategoryDescription(category.getCategoryDescription());

            return Optional.of(repo.save(optCategory));
        }

        return Optional.empty();
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }
}
