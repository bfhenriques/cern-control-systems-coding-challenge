package ch.cern.todo.controllers;

import ch.cern.todo.entities.Category;
import ch.cern.todo.services.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "https://localhost:8080")
@RestController
public class CategoryController {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add-category")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        try {
            logger.info(String.format("Creating category: %s.", category));
            Category persistedCategory = categoryService.create(category);

            logger.info(String.format("Created category: %s.", persistedCategory));
            return new ResponseEntity<>(persistedCategory, HttpStatus.CREATED);
        } catch (Exception ex) {
            logger.error("Error creating category.", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            logger.info("Getting all categories.");
            List<Category> allCategories = categoryService.getAll();

            if (allCategories.isEmpty()) {
                logger.info("No categories found.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            logger.info(String.format("All categories: %s.", allCategories));
            return new ResponseEntity<>(allCategories, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting all categories.", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") int categoryId) {
        try {
            logger.info(String.format("Getting category with id=%s.", categoryId));
            Optional<Category> persistedCategory = categoryService.getById(categoryId);

            if (persistedCategory.isEmpty()) {
                logger.info(String.format("Category with id=%s not found.", categoryId));
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            logger.info(String.format("Category: %s.", persistedCategory));
            return new ResponseEntity<>(persistedCategory.get(), HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(String.format("Error getting category with id=%s.", categoryId), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("categories/{id}")
    public ResponseEntity<Category> editCategory(@PathVariable("id") int categoryId, @RequestBody Category category) {
        try {
            logger.info(String.format("Editing category with id=%s.", categoryId));
            Optional<Category> persistedCategory = categoryService.edit(categoryId, category);

            if (persistedCategory.isEmpty()) {
                logger.info(String.format("Category with id=%s not found.", categoryId));
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            logger.info(String.format("Edited category: %s.", persistedCategory));
            return new ResponseEntity<>(persistedCategory.get(), HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(String.format("Error editing category with id=%s.", categoryId), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("categories/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") int categoryId) {
        try {
            logger.info(String.format("Deleting category with id=%s.", categoryId));
            categoryService.delete(categoryId);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(String.format("Error deleting category with %s.", categoryId), ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
