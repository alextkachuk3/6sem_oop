package com.example.oop.services;

import com.example.oop.dto.response.GoodsResponse;
import com.example.oop.repositories.GoodsRepository;

import java.util.List;

public class GoodsService {
    private final GoodsRepository goodsRepository = new GoodsRepository();

    public List<GoodsResponse> findAll() {
        return goodsRepository.findAll().stream()
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
