package com.example.oop.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminEntity {
    private Long id;
    private String username;
}
