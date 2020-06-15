package com.example.inter;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.User;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByName(String name);
    boolean existsByName(String name);
}
