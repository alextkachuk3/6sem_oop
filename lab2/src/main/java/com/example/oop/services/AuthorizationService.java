package com.example.oop.services;

import com.example.oop.entity.Admin;
import com.example.oop.entity.Client;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    public Client getCurrentClient() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (Client) principal;
    }

    public Admin getCurrentAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (Admin) principal;
    }
}
