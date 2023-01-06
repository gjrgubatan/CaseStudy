package com.example.cs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private DataSource dataSource;
     
    @Bean
    public UserDetailsService userDetailsService() {
        return new AdminDetailsService();
    }
     
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return NoOpPasswordEncoder.getInstance();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .requestMatchers("/", "/employees").authenticated()
            .anyRequest().permitAll()
            .and()
            .formLogin()
                .usernameParameter("email")
                .defaultSuccessUrl("/employees")
                .permitAll()
            .and()
            .logout().logoutSuccessUrl("/").permitAll();
        
        return http.build();
    }
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	            http.authorizeRequests()
//	    .requestMatchers("/", "/employees").authenticated()
//	    .anyRequest().authenticated()
//	    .and()
//	    .formLogin()
//	    .loginPage("/")
//	    .usernameParameter("email")
//	    .defaultSuccessUrl("/employees")
//	    .permitAll()
//	    .and()
//	    .logout()
//	    .invalidateHttpSession(true)
//	    .clearAuthentication(true)
//	    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//	    .logoutSuccessUrl("/").permitAll();
//	            return http.build();
//    }
     
     
}
