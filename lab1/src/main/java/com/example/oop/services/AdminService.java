package com.example.oop.services;

import com.example.oop.entities.AdminEntity;
import com.example.oop.entities.GoodsEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.oop.dto.request.DisableRequest;
import com.example.oop.dto.request.RefreshRequest;
import com.example.oop.dto.response.CurrentAdminResponse;
import com.example.oop.repositories.AdminRepository;
import com.example.oop.repositories.ClientRepository;
import com.example.oop.repositories.GoodsRepository;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

public class AdminService {
    private final AuthorizationService authorizationService = new AuthorizationService();
    private final AdminRepository adminRepository = new AdminRepository();
    private final ClientRepository clientRepository = new ClientRepository();
    private final GoodsRepository goodsRepository = new GoodsRepository();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CurrentAdminResponse getCurrentAdmin(HttpServletRequest request) {
        String username = authorizationService.getUsername(request);
        AdminEntity adminEntity = adminRepository.findByUsername(username);
        return CurrentAdminResponse.builder()
                .id(adminEntity.getId())
                .username(adminEntity.getUsername())
                .build();
    }

    @SneakyThrows
    public void refresh(HttpServletRequest request) {
        RefreshRequest body = objectMapper.readValue(
                request.getReader().lines().collect(Collectors.joining(System.lineSeparator())),
                RefreshRequest.class);
        GoodsEntity goodsEntity = goodsRepository.findById(body.getId());
        goodsRepository.updateCount(goodsEntity.getId(), goodsEntity.getCount() + body.getCount());
    }

    @SneakyThrows
    public void disable(HttpServletRequest request) {
        DisableRequest body = objectMapper.readValue(
                request.getReader().lines().collect(Collectors.joining(System.lineSeparator())),
                DisableRequest.class);
        clientRepository.disable(body.getId());
    }

    @SneakyThrows
    public void enable(HttpServletRequest request) {
        DisableRequest body = objectMapper.readValue(
                request.getReader().lines().collect(Collectors.joining(System.lineSeparator())),
                DisableRequest.class);
        clientRepository.enable(body.getId());
    }
}
