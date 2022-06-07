package com.example.oop.servlets;

import com.example.oop.exceptions.HttpException;
import com.example.oop.services.AdminService;
import com.example.oop.services.AuthorizationService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/enable")
public class EnableServlet extends HttpServlet {
    private final AdminService adminService = new AdminService();
    private final AuthorizationService authorizationService = new AuthorizationService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (!authorizationService.hasAuthority(request, "admin")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            adminService.enable(request);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (HttpException e) {
            response.setStatus(e.getHttpCode());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
