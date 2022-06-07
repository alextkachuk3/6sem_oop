package com.example.oop.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminResponse {
    private Long id;
    private String username;
}
