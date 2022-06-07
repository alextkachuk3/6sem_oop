package com.example.oop.services;

import com.example.oop.dto.request.RefreshRequest;
import com.example.oop.dto.response.AdminResponse;
import com.example.oop.entity.Admin;
import com.example.oop.entity.Goods;
import com.example.oop.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AuthorizationService authorizationService;
    private final GoodsRepository goodsRepository;

    public AdminResponse getAdmin() {
        Admin adminEntity = authorizationService.getCurrentAdmin();
        return AdminResponse.builder()
                .id(adminEntity.getId())
                .username(adminEntity.getUsername())
                .build();
    }

    @SneakyThrows
    public void refresh(RefreshRequest request) {
        Goods goodsEntity = goodsRepository.getById(request.getId());
        goodsEntity.setCount(goodsEntity.getCount() + request.getCount());
        goodsRepository.save(goodsEntity);
    }
}
