package com.example.oop;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebFilter(asyncSupported = true, urlPatterns = {"/*"})
public class CORSInterceptor implements Filter {
    public static final List<String> allowedOrigins = List.of("http://localhost:3000", "http://127.0.0.1:3000");
    public static final String APPLICATION_JSON = "application/json";
    public static final String UTF_8 = "UTF-8";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String origin = request.getHeader("Origin");
        if (origin != null && allowedOrigins.contains(origin)) {
            ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", origin);
            ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "*");
            ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods",
                    "GET, OPTIONS, HEAD, PUT, POST, DELETE");
            if (request.getMethod().equals("OPTIONS")) {
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                return;
            }
        }
        response.setContentType(APPLICATION_JSON);
        response.setCharacterEncoding(UTF_8);
        filterChain.doFilter(request, servletResponse);
    }
}
