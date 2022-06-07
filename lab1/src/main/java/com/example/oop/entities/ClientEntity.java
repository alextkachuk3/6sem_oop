package com.example.oop.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientEntity {
    private Long id;
    private String username;
    private Long amount;
    private Boolean disable;
}
