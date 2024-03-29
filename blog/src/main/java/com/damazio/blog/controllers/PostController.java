
package com.damazio.blog.controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.damazio.blog.models.Post;
import com.damazio.blog.dtos.PostRequest;
import com.damazio.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create-with-user/{userId}")
    public ResponseEntity<Post> createPostWithUserAndCategories(
            @PathVariable Long userId,
            @RequestBody PostRequest postRequest)
           {
        Post createdPost = postService.createPostWithUserAndCategories(userId, postRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdPost.getId()).toUri();
        return ResponseEntity.created(uri).body(createdPost);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        Post updatedPost = postService.updatePost(id, postRequest);
        return ResponseEntity.ok(updatedPost);
    }
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
            return ResponseEntity.noContent().location(location).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{postId}/categories/{categoryId}")
    public ResponseEntity<String> removeCategoryFromPost(@PathVariable Long postId,
        @PathVariable Long categoryId) {
        postService.removeCategoryFromPost(postId, categoryId);
        return ResponseEntity.ok("Category removed from post.");
    }

}