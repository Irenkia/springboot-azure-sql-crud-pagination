package com.example.springbootazuresqlcrudpagination.repository;

import com.example.springbootazuresqlcrudpagination.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByCountryContaining(String country, Pageable pageable);
}
