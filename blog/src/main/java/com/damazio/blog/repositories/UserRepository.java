package com.damazio.blog.repositories;

import com.damazio.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Se necessário, adicione métodos personalizados de consulta aqui
}
