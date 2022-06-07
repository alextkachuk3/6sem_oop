package com.example.oop.controler;

import com.example.oop.dto.request.RefreshRequest;
import com.example.oop.dto.response.AdminResponse;
import com.example.oop.dto.response.ClientResponse;
import com.example.oop.services.AdminService;
import com.example.oop.services.ClientService;
import com.example.oop.dto.request.DisableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final ClientService clientService;

    @GetMapping("/servlet_war_exploded/current-admin")
    public AdminResponse getCurrentAdmin() {
        return adminService.getAdmin();
    }

    @PutMapping("/servlet_war_exploded/refresh")
    public void refresh(@RequestBody RefreshRequest request) {
        adminService.refresh(request);
    }

    @GetMapping("/servlet_war_exploded/clients")
    public List<ClientResponse> getClients() {
        return clientService.getAll();
    }

    @PostMapping("/servlet_war_exploded/disable")
    public void disable(@RequestBody DisableRequest request) {
        clientService.disable(request);
    }

    @PostMapping("/servlet_war_exploded/enable")
    public void enable(@RequestBody DisableRequest request) {
        clientService.enable(request);
    }
}
