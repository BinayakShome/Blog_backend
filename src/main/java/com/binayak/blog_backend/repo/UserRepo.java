package com.binayak.blog_backend.repo;

import com.binayak.blog_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

}