package com.damazio.blog.services;

import com.damazio.blog.dtos.PostRequest;
import com.damazio.blog.models.Category;
import com.damazio.blog.models.Post;
import com.damazio.blog.models.User;
import com.damazio.blog.repositories.CategoryRepository;
import com.damazio.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    @Autowired
    public PostService(PostRepository postRepository, UserService userService, CategoryService categoryService, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    public Post createPostWithUserAndCategories(Long userId, PostRequest postRequest) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        List<Category> categories = categoryService.getCategoriesByIds(postRequest.getCategoryIds());

        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setCreateAt(Instant.now());
        post.setUpdateAt(Instant.now());
        post.setUser(user);
        post.setCategories(categories);

        return postRepository.save(post);
    }

    public Post addCategoryToPost(Long postId, Long categoryIds) {
        Post post = getPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));

        List<Category> categories = categoryService.getCategoriesByIds(Collections.singletonList(categoryIds));
        post.getCategories().addAll(categories);

        return postRepository.save(post);
    }
    public List<Category> getCategoriesByIds(List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }



    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post updatePost(Long id, PostRequest postRequest) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setTitle(postRequest.getTitle());
            post.setDescription(postRequest.getDescription());
            post.setUpdateAt(Instant.now());
            return postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found with ID: " + id);
        }
    }

    public void deletePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // Remove as associações com categorias
            post.getCategories().clear(); // Remove todas as categorias

            postRepository.delete(post);
        } else {
            throw new RuntimeException("Post not found with ID: " + postId);
        }
    }
    public void removeCategoryFromPost(Long postId, Long categoryId) {
        Post post = getPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));

        Category category = categoryService.getCategoryById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        post.getCategories().remove(category);

        postRepository.save(post);
    }
}