package com.damazio.blog.services;

import com.damazio.blog.dtos.PostRequest;
import com.damazio.blog.models.Post;
import com.damazio.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(PostRequest postRequest) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setCreateAt(Instant.now());
        post.setUpdateAt(Instant.now());

        return postRepository.save(post);
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

    public void deletePost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            postRepository.delete(optionalPost.get());
        } else {
            throw new RuntimeException("Post not found with ID: " + id);
        }
    }
}
