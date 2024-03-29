package com.damazio.blog.services;

import com.damazio.blog.models.Category;
import com.damazio.blog.models.Post;
import com.damazio.blog.repositories.CategoryRepository;
import com.damazio.blog.dtos.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category updateCategory(Long id, CategoryRequest categoryRequest) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(categoryRequest.getName());
            return categoryRepository.save(category);
        } else {
            throw new RuntimeException("Category not found with ID: " + id);
        }
    }
    public List<Category> getCategoriesByIds(List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }


    public void deleteCategory(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();

            // Remove as associações com posts
            for (Post post : category.getPosts()) {
                post.getCategories().remove(category);
            }

            categoryRepository.delete(category);
        } else {
            throw new RuntimeException("Category not found with ID: " + categoryId);
        }
    }
}