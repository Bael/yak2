package ru.otus.spring.hw.kanban.security;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig)  {
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    System.out.println("CORSFilter HTTP Request: " + request.getMethod());

    // Authorize (allow) all domains to consume the content
    ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
    ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
    ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,X-Auth-Token");

    HttpServletResponse resp = (HttpServletResponse) servletResponse;

//     For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
    if (request.getMethod().equals("OPTIONS")) {
      resp.setStatus(HttpServletResponse.SC_OK);
      return;
    }

    // pass the request along the filter chain
    chain.doFilter(request, servletResponse);
  }

  public void destroy() {
  }
}
