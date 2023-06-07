package com.social.media.platform.core.security;

import com.social.media.platform.core.services.PersonDetailsService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PersonDetailsService personDetailsService;
    private final JWTFilter jwtFilter;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, JWTFilter jwtFilter) {
        this.personDetailsService = personDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                        .antMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-resources/**","/swagger-ui.html","/webjars/**",
                                "/registration","/login").permitAll()
                        .antMatchers(HttpMethod.GET,"/post/activePost/{userId}/{page}/{size}").access("@guard.checkUserId(authentication,#id)")
                        .antMatchers(HttpMethod.PATCH,"/post/{id}/updatedPost").access("@guard.checkUserId(authentication,#id)")
                        .antMatchers(HttpMethod.DELETE,"/post/{id}").access("@guard.checkUserId(authentication,#id)")
                        .antMatchers(HttpMethod.DELETE,"/friendship/{friendshipId1}/{friendshipId2}").access("@guard.checkUserId(authentication,#id)")
                        .antMatchers(HttpMethod.DELETE,"/friendship/subscription/{friendshipId}").access("@guard.checkUserId(authentication,#id)")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}




