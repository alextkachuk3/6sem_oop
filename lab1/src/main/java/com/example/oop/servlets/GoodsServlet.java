package com.example.oop.servlets;

import com.example.oop.services.GoodsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.oop.exceptions.HttpException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/goods")
public class GoodsServlet extends HttpServlet {
    private final GoodsService goodsService = new GoodsService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            Object result = goodsService.findAll();
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
