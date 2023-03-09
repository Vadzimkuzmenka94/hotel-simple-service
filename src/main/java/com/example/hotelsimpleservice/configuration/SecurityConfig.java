package com.example.hotelsimpleservice.configuration;

import com.example.hotelsimpleservice.security.implementation.SecurityServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final String LOGIN_PAGE = "/auth/login";
    private final String GENERAL_PAGE = "/home/**";
    private final String REGISTRATION_PAGE = "/auth/registration";
    private final String SUCCESSFUL_REGISTRATION_PAGE = "/auth/successful-registration";
    private final String ERROR_PAGE = "/auth/successful-registration";
    private final String HOME_PAGE = "/actions";
    private final String PROCESS_LOGIN = "/process_login";
    private final String LOGIN_ERROR = "/auth/login?error";
    private final String LOGOUT = "/logout";
    private final SecurityServiceImplementation securityServiceImplementation;

    @Autowired
    public SecurityConfig(SecurityServiceImplementation securityServiceImplementation) {
        this.securityServiceImplementation = securityServiceImplementation;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.
                    csrf().disable()
                    .authorizeRequests()
                    .antMatchers(LOGIN_PAGE, ERROR_PAGE, REGISTRATION_PAGE, SUCCESSFUL_REGISTRATION_PAGE, GENERAL_PAGE)
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage(LOGIN_PAGE)
                    .loginProcessingUrl(PROCESS_LOGIN)
                    .defaultSuccessUrl(HOME_PAGE, true)
                    .failureUrl(LOGIN_ERROR)
                    .and()
                    .logout()
                    .logoutUrl(LOGOUT)
                    .logoutSuccessUrl(LOGIN_PAGE);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(securityServiceImplementation)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}