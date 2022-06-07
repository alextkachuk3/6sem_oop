package com.example.oop.repository;

import com.example.oop.entity.Client;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepositoryImplementation<Client, Long> {
    Optional<Client> findByUsername(String username);
}
