package com.example.oop.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshRequest {
    private Long id;
    private Long count;
}
