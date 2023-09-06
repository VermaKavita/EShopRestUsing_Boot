package com.ecom.shopping_cart.security;

import com.ecom.shopping_cart.jwtAuth.JwtAuthenticationEntryPoint;
import com.ecom.shopping_cart.jwtAuth.JwtAuthenticationFilter;
import com.ecom.shopping_cart.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	CustomerService customerService;
	@Autowired
	private JwtAuthenticationEntryPoint point;
	@Autowired
	private JwtAuthenticationFilter filter;

	private String WISHLIST[]={
			"/jwt",
			"/product/**",
			"/swagger-ui/**",
			"/v3/api-docs/",
			"/hello",
			"/register"
	};
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder ( passwordEncoder () );
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors (cors->cors.disable ()).csrf (csrf->csrf.disable ())
				.authorizeHttpRequests(requests -> requests.requestMatchers(WISHLIST).permitAll().anyRequest ().authenticated ()				)
				.exceptionHandling(handling -> handling.authenticationEntryPoint(point))
				.sessionManagement(management -> management.sessionCreationPolicy( SessionCreationPolicy.STATELESS));
		http.authenticationProvider ( daoAuthenticationProvider () );
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}

}
