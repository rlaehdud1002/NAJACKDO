package com.najackdo.server.core.configuration;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.najackdo.server.core.filter.JWTFilter;
import com.najackdo.server.domain.auth.handler.CustomAuthenticationDeniedHandler;
import com.najackdo.server.domain.auth.handler.CustomAuthenticationEntryPoint;
import com.najackdo.server.domain.auth.handler.CustomSuccessHandler;
import com.najackdo.server.domain.auth.repository.CustomAuthorizationRequestRepository;
import com.najackdo.server.domain.auth.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomOAuth2UserService customOAuth2UserService;
	private final CustomSuccessHandler customSuccessHandler;
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;
	private final CustomAuthenticationDeniedHandler authenticationDeniedHandler;
	private final CustomAuthorizationRequestRepository customAuthorizationRequestRepository;
	private final JWTFilter jwtFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
				CorsConfiguration configuration = new CorsConfiguration();
				configuration.setAllowedOrigins(List.of("http://localhost:3000", "https://www.najackdo.kro.kr"));
				configuration.setAllowedMethods(Collections.singletonList("*"));
				configuration.setAllowCredentials(true);
				configuration.setAllowedHeaders(Collections.singletonList("*"));
				configuration.setExposedHeaders(List.of("Set-Cookie", "Authorization"));
				return configuration;
			}))
			.exceptionHandling(configurer -> configurer
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(authenticationDeniedHandler)
			)
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.sessionManagement(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll())
			.oauth2Login((oauth2) -> oauth2
				.authorizationEndpoint(authorization ->
					authorization.baseUri("/oauth2/authorization")
						.authorizationRequestRepository(customAuthorizationRequestRepository)
				)
				.redirectionEndpoint(redirection -> redirection.baseUri("/*/oauth2/code/*"))
				.userInfoEndpoint(
					(userInfoEndpointConfig) -> userInfoEndpointConfig.userService(customOAuth2UserService))
				.successHandler(customSuccessHandler)
			)
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}
}
