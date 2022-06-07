package com.example.oop.services;

import com.example.oop.entities.ClientEntity;
import com.example.oop.entities.GoodsEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.oop.dto.request.OrderRequest;
import com.example.oop.dto.response.CurrentClientResponse;
import com.example.oop.repositories.ClientRepository;
import com.example.oop.repositories.GoodsRepository;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class ClientService {
    private final AuthorizationService authorizationService = new AuthorizationService();
    private final ClientRepository clientRepository = new ClientRepository();
    private final GoodsRepository goodsRepository = new GoodsRepository();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<CurrentClientResponse> getAll() {
        return clientRepository.finAll().stream()
                .map(entity -> CurrentClientResponse.builder()
                        .id(entity.getId())
                        .username(entity.getUsername())
                        .amount(entity.getAmount())
                        .disable(entity.getDisable())
                        .build())
                .toList();
    }

    public CurrentClientResponse getCurrentUser(HttpServletRequest request) {
        String username = authorizationService.getUsername(request);
        ClientEntity client = clientRepository.findByUsername(username);
        return CurrentClientResponse.builder()
                .id(client.getId())
                .username(client.getUsername())
                .amount(client.getAmount())
                .disable(client.getDisable())
                .build();
    }

    @SneakyThrows
    public void order(HttpServletRequest request) {
        OrderRequest body = objectMapper.readValue(
                request.getReader().lines().collect(Collectors.joining(System.lineSeparator())),
                OrderRequest.class);
        String username = authorizationService.getUsername(request);
        ClientEntity client = clientRepository.findByUsername(username);
        GoodsEntity goodsEntity = goodsRepository.findById(body.getId());
        goodsRepository.updateCount(goodsEntity.getId(), goodsEntity.getCount() - body.getCount());
        clientRepository.updateAmount(client.getId(), client.getAmount() - goodsEntity.getPrice() * body.getCount());
    }
}
