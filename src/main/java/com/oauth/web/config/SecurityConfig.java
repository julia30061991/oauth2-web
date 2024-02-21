package com.oauth.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure (HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher("/**").authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/show", "/create").hasAuthority("ROLE_USER")
                .antMatchers("/delete").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .oauth2Login();
    }
}