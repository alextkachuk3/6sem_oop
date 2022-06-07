package com.example.oop.controler;

import com.example.oop.dto.response.ClientResponse;
import com.example.oop.services.ClientService;
import com.example.oop.dto.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/servlet_war_exploded/current-client")
    public ClientResponse getCurrentClient() {
        return clientService.getClient();
    }

    @PostMapping("/servlet_war_exploded/order")
    public void order(@RequestBody OrderRequest request) {
        clientService.order(request);
    }
}
