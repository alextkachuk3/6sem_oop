package com.example.oop.repository;

import com.example.oop.entity.Admin;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepositoryImplementation<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}
