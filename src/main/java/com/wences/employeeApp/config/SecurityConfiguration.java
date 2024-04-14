package com.wences.employeeApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.wences.employeeApp.service.UserService;

@Configuration
@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
public class SecurityConfiguration {

	@Autowired
	private UserService userService;
	
        @Bean
        public static BCryptPasswordEncoder passwordEncoder() {
           return new BCryptPasswordEncoder();
        }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests((authorize) ->
						authorize.requestMatchers("/registration**" , "/js/**" , "/css/**" , "/img/**" , "/assets/**").permitAll()
				
//								.requestMatchers("/index**").hasRole("USER")
								.anyRequest().authenticated() 
				).formLogin(
						form -> form
								.loginPage("/login")
								.permitAll()
				).logout(
						logout -> logout
								.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
								.permitAll()
				);
		return http.build();
	}
        

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userService)
				.passwordEncoder(passwordEncoder());
	}
}
