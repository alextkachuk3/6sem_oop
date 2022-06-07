package com.example.oop.services;

import com.example.oop.dto.response.ClientResponse;
import com.example.oop.entity.Client;
import com.example.oop.entity.Goods;
import com.example.oop.dto.request.DisableRequest;
import com.example.oop.dto.request.OrderRequest;
import com.example.oop.repository.ClientRepository;
import com.example.oop.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final AuthorizationService authorizationService;
    private final ClientRepository clientRepository;
    private final GoodsRepository goodsRepository;

    public List<ClientResponse> getAll() {
        return clientRepository.findAll().stream()
                .map(client ->
                        ClientResponse.builder()
                                .id(client.getId())
                                .username(client.getUsername())
                                .amount(client.getAmount())
                                .disable(client.isDisable())
                                .build())
                .toList();
    }

    public ClientResponse getClient() {
        Client client = authorizationService.getCurrentClient();
        return ClientResponse.builder()
                .id(client.getId())
                .username(client.getUsername())
                .amount(client.getAmount())
                .disable(client.isDisable())
                .build();
    }

    public void order(OrderRequest request) {
        Client client = authorizationService.getCurrentClient();
        Goods goodsEntity = goodsRepository.getById(request.getId());
        goodsEntity.setCount(goodsEntity.getCount() - request.getCount());
        client.setAmount(client.getAmount() - goodsEntity.getPrice() * request.getCount());
        goodsRepository.save(goodsEntity);
        clientRepository.save(client);
    }

    public void disable(DisableRequest request) {
        Client client = clientRepository.getById(request.getId());
        client.setDisable(true);
        clientRepository.save(client);
    }

    public void enable(DisableRequest request) {
        Client client = clientRepository.getById(request.getId());
        client.setDisable(false);
        clientRepository.save(client);
    }
}
