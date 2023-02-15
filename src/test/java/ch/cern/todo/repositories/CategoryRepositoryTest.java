package ch.cern.todo.repositories;

import ch.cern.todo.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    CategoryRepository repo;

    @Test
    void shouldFindNoCategories() {
        List<Category> categories = repo.findAll();

        assertTrue(categories.isEmpty());
    }

    @Test
    void shouldCreateCategory() {
        Category category = repo.save(new Category("dummy name", "dummy description"));

        assertEquals("dummy name", category.getCategoryName());
        assertEquals("dummy description", category.getCategoryDescription());
    }

    @Test
    void shouldFindCategories() {
        Category category1 = new Category("dummy name 1", "dummy description 1");
        Category category2 = new Category("dummy name 2", "dummy description 2");

        entityManager.persist(category1);
        entityManager.persist(category2);

        List<Category> allCategories = repo.findAll();

        assertEquals(2, allCategories.size());
        entityManager.clear();
    }

    @Test
    void shouldFindCategoryById() {
        Category category1 = new Category("dummy name 1", "dummy description 1");

        Category persistedCategory = entityManager.persist(category1);

        Optional<Category> category = repo.findById(persistedCategory.getCategoryId());

        assertTrue(category.isPresent());
        assertEquals(category1.getCategoryName(), category.get().getCategoryName());
        assertEquals(category1.getCategoryDescription(), category.get().getCategoryDescription());
        entityManager.clear();
    }

    @Test
    void shouldDeleteCategoryById() {
        Category category1 = new Category("dummy name 1", "dummy description 1");

        Category persistedCategory = entityManager.persist(category1);
        int persistedId = persistedCategory.getCategoryId();
        repo.deleteById(persistedId);
        Optional<Category> category = repo.findById(persistedId);

        assertTrue(category.isEmpty());
        entityManager.clear();
    }
}