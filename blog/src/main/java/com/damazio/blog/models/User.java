package com.damazio.blog.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)

    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean authorizedUser;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

}
