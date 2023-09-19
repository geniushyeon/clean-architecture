package com.geniushyeon.architecture.filter;

import com.geniushyeon.architecture.exception.BaseException;
import com.geniushyeon.architecture.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private static String[] NO_CHECK_URIS = {
            "/members/sign-up",
            "/members",
            "/members/login",
            "/login"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info(request.getRequestURI());
        if (!isTokenRequired(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // TODO: extract to method
        if (ObjectUtils.isEmpty(request.getHeader(AUTHORIZATION_HEADER))) {
            log.error("accessToken is empty");
            // TODO: throw exception
            return;
        }

        if (!request.getHeader(AUTHORIZATION_HEADER).startsWith(BEARER_PREFIX)) {
            log.error("accessToken is not Bearer type");
            // TODO: throw exception
            return;
        }

        String accessToken = request.getHeader("Authorization").replace("Bearer ", "");

        try {
            Authentication authentication = tokenService.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("set authentication complete");
        } catch (BaseException e) {
            log.error("token valid error: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private boolean isTokenRequired(String requestURI) {
        for (String uri : NO_CHECK_URIS) {
            if (uri.equals(requestURI)) {
                return false;
            }
        }

        return true;
    }
}
