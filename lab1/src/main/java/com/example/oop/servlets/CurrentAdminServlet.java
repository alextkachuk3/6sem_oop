package com.example.oop.servlets;

import com.example.oop.services.AdminService;
import com.example.oop.services.AuthorizationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.oop.exceptions.HttpException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/current-admin")
public class CurrentAdminServlet extends HttpServlet {
    private final AdminService adminService = new AdminService();
    private final AuthorizationService authorizationService = new AuthorizationService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (!authorizationService.hasAuthority(request, "admin")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            Object result = adminService.getCurrentAdmin(request);
            response.getWriter().print(objectMapper.writeValueAsString(result));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (HttpException e) {
            response.setStatus(e.getHttpCode());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
