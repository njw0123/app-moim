package org.edupoll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
			.csrf(t->t.disable())
			.authorizeHttpRequests(t -> t
				.requestMatchers("/", "/user/login", "/user/join", "/moim").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(t -> t
					.usernameParameter("id")
					.passwordParameter("pass")
					.loginPage("/user/login")
					.defaultSuccessUrl("/")
			)
			.exceptionHandling(t -> t
					.accessDeniedPage("/user/access/denied")
			);
		
		return http.build();
	}
}
