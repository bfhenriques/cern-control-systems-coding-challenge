package ch.cern.todo.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private static final String categoryName = "dummy category name";
    private static final String categoryDescription = "dummy category description";

    @Test
    void testCorrectCreationOfCategoryWithParamsConstructor() {
        Category category = new Category(categoryName, categoryDescription);
        executeAssertions(category);
    }

    @Test
    void testCorrectCreationOfCategoryWithNoParamsConstructor() {
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setCategoryDescription(categoryDescription);
        executeAssertions(category);
    }

    private static void executeAssertions(Category category) {
        assertEquals(0, category.getCategoryId());
        assertEquals(categoryName, category.getCategoryName());
        assertEquals(categoryDescription, category.getCategoryDescription());
    }
}