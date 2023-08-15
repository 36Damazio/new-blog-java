package com.damazio.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long id;

    private List <Long>  CategoryIds;

    private String title;

    private String description;

    private Instant updateAt;

    private Instant createAt;
}
