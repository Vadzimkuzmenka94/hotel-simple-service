
package com.example.hotelsimpleservice.configuration;

import com.example.hotelsimpleservice.security.implementation.SecurityServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
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
                .antMatchers("/auth/login", "/error", "/auth/registration")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/auth/registration", true)
                .failureUrl("/auth/login?error");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(securityServiceImplementation);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

/*    @Bean
    public PrincipalExtractor principalExtractor(CustomerRepository repository) {
        return map -> {
            Long id = (Long) map.get("sub");
            Customer customer = repository.findById(id).orElseGet(() -> {
                        Customer newCustomer = new Customer();
                newCustomer.setCustomer_id(id);
                newCustomer.setName((String) map.get("name"));
                newCustomer.setSurname("Empty");
                newCustomer.setLogin((String) map.get("email"));
                newCustomer.setEmail((String) map.get("email"));
                newCustomer.setPassword("QwE1r*f1r");
                newCustomer.setCardNumber("1111-1111-1111-1111");
                        return newCustomer;
                    }
            );
            return repository.save(customer);
        };
    }
}*/

/*    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }*/
             /*   .antMatchers(HttpMethod.GET, HTTP_FOR_TAG, HTTP_FOR_ORDERS, HTTP_FOR_GC).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HttpMethod.POST, HTTP_FOR_GC, HTTP_FOR_TAG).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HTTP_AUTH_LOGIN, HTTP_AUTH_REGISTRATION, HTTP_ERROR, HTTP_TOKEN)
                .permitAll().anyRequest().authenticated()
                .and()
                .formLogin().loginPage(HTTP_AUTH_LOGIN)
                .loginProcessingUrl(HTTP_PROCESS_LOGIN)
                .failureUrl(HTTP_AUTH_LOGIN_ERROR)
                .and()
                .logout()
                .logoutUrl(HTTP_LOGOUT)
                .logoutSuccessUrl(HTTP_AUTH_LOGIN);
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);*/






/*    @Autowired
    public SecurityConfig(SecurityService securityService) {
        this.securityService = securityService;
    }*//*


    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(securityService);
    }


    */


/*    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



   @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }*

}*/


