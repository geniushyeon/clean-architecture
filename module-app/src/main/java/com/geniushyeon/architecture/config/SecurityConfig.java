package com.geniushyeon.architecture.config;

import com.geniushyeon.architecture.handler.CustomAccessDeniedHandler;
import com.geniushyeon.architecture.handler.CustomAuthenticationEntryPoint;
import com.geniushyeon.architecture.handler.LoginSuccessHandler;
import com.geniushyeon.architecture.service.MemberService;

import com.geniushyeon.architecture.service.TokenService;
import jakarta.servlet.DispatcherType;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final MemberService memberService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/h2-console/**", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().disable() // 모든 cross origin 요청 차단
                // custom exception handling
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                // 세션 STATELESS 설정
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/members/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/members/login")
                        .loginProcessingUrl("/login")
                        .successHandler(loginSuccessHandler()))
                .logout(withDefaults())
                .apply(new JwtSecurityConfig(tokenService))
                .and()
                .apply(new CustomAuthenticationConfigurer(memberService, passwordEncoder))
        ;

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(tokenService, memberService);
    }
}
