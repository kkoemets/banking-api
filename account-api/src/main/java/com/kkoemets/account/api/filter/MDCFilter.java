package com.kkoemets.account.api.filter;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.UUID.randomUUID;
import static org.slf4j.LoggerFactory.getLogger;

public class MDCFilter extends OncePerRequestFilter {
    private static final Logger log = getLogger(MDCFilter.class);
    private static final int REQUEST_ID_LENGTH = 7;

    @Override
    public void destroy() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String requestId = randomUUID().toString().substring(0, REQUEST_ID_LENGTH);
        log.info("Adding requestId-{} to request", requestId);

        MDC.put("requestId", requestId);
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

}
