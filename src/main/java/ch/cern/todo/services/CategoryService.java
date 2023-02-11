package ch.cern.todo.services;

import ch.cern.todo.entities.Category;
import ch.cern.todo.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements IRepositoryService<Category> {

    private CategoryRepository repo;

    @Override
    public Category create(Category entity) {
        return repo.save(entity);
    }

    @Override
    public List<Category> getAll() {
        return repo.findAll();
    }

    @Override
    public Category getById(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Category edit(Category category) {
        Category persistedCategory = repo.findById(category.getCategoryId()).orElse(null);

        if (persistedCategory != null) {
            persistedCategory.setCategoryName(category.getCategoryName());
            persistedCategory.setCategoryDescription(category.getCategoryDescription());

            return repo.save(persistedCategory);
        }

        return repo.save(category);
    }

    @Override
    public Boolean delete(int id) {
        repo.deleteById(id);
        return true;
    }

    /*
    public Category createCategory(Category category) {
        return repo.save(category);
    }

    public List<Category> getAllCategory() {
        return repo.findAll();
    }

    public Category getCategoryById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Category editCategory(Category category) {
        Category persistedCategory = repo.findById(category.getCategoryId()).orElse(null);

        if (persistedCategory != null) {
            persistedCategory.setCategoryName(category.getCategoryName());
            persistedCategory.setCategoryDescription(category.getCategoryDescription());

            return repo.save(persistedCategory);
        }

        return repo.save(category);
    }

    public Boolean deleteCategory(int id) {
        repo.deleteById(id);
        return true;
    }
    */
}
