package com.example.oop.controler;

import com.example.oop.dto.response.GoodsResponse;
import com.example.oop.services.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;

    @GetMapping("/servlet_war_exploded/goods")
    public List<GoodsResponse> getCurrentAdmin() {
        return goodsService.findAll();
    }
}
