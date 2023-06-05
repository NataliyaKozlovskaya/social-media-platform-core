package com.social.media.platform.core.security;

import com.social.media.platform.core.services.PersonDetailsService;
import org.modelmapper.ModelMapper;
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


//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PersonDetailsService personDetailsService;
    private final JWTFilter jwtFilter;

    public SecurityConfig(PersonDetailsService personDetailsService, JWTFilter jwtFilter) {
        this.personDetailsService = personDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()// сначаа админ потом продукт
                .authorizeRequests()
//                        .antMatchers("/person","/person/admin/{id}",
//                                "/order", "/order/{id}","/person/{personId}/order", "/order/{id}",
//                                "/admin/**",
//                                "/dressModel/admin/**", "/dressSize/admin/**",
//                                "/color/admin/**", "/product/admin").hasAuthority("ADMIN")
//                                "/color/admin/**", "/product/admin").hasAuthority("USER")

                .antMatchers("/registration","/login", "/error","/{id}/updatedPost", "/allPost/{id}").permitAll()
//                        .antMatchers(HttpMethod.PATCH,"/{id}/updatedPost").access("@guard.checkUserId(authentication,#id)")
//                        .antMatchers(HttpMethod.DELETE,"/{id}").access("@guard.checkUserId(authentication,#id)")
//                        .antMatchers(HttpMethod.PATCH,"/person/{id}").access("@guard.checkUserId(authentication,#id)")
//                        .antMatchers(HttpMethod.GET,"/person/{personId}/order").access("@guard.checkUserId(authentication,#id)")
//                        .antMatchers(HttpMethod.GET,"/order/{id}").access("@guard.checkUserId(authentication,#id)")



//                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

               //httpBasic(withDefaults());
               //return http.build();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncoder());
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();//будет заниматься шифрованием
        //return NoOpPasswordEncoder.getInstance();
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




