package com.example.lab8.Repository;

import com.example.lab8.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByNombre(String nombre);


}
