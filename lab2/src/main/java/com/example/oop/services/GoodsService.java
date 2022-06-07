package com.example.oop.services;

import com.example.oop.dto.response.GoodsResponse;
import com.example.oop.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;

    public List<GoodsResponse> findAll() {
        return goodsRepository.findByOrderById().stream()
                .map(entity ->
                        GoodsResponse.builder()
                                .id(entity.getId())
                                .name(entity.getName())
                                .price(entity.getPrice())
                                .count(entity.getCount())
                                .build())
                .toList();
    }
}
