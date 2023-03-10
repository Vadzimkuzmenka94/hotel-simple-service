package com.example.hotelsimpleservice.controller.auth_controller;

import com.example.hotelsimpleservice.dto.CustomerDto;
import com.example.hotelsimpleservice.emailNotifications.MailSender;
import com.example.hotelsimpleservice.emailNotifications.Messages;
import com.example.hotelsimpleservice.exceptions.AppException;
import com.example.hotelsimpleservice.exceptions.ErrorCode;
import com.example.hotelsimpleservice.model.Customer;
import com.example.hotelsimpleservice.security.jwt.AuthRequest;
import com.example.hotelsimpleservice.security.jwt.AuthResponse;
import com.example.hotelsimpleservice.security.jwt.JwtUtil;
import com.example.hotelsimpleservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final String CUSTOMER_ATTRIBUTE = "customer";
    private final CustomerService customerService;
    private final MailSender mailSender;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtTokenUtil;

    @Autowired
    public AuthController(CustomerService customerService, MailSender mailSender, AuthenticationManager authenticationManager, JwtUtil jwtTokenUtil) {
        this.customerService = customerService;
        this.mailSender = mailSender;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute(CUSTOMER_ATTRIBUTE, new Customer());
        return "pages/auth-pages/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") CustomerDto customer) {
        customerService.createCustomer(customer);
        mailSender.sendEmail(customer.getEmail(), "message", Messages.CREATE_BOOKING_MESSAGE.getMessage());
        log.info(Messages.CREATE_BOOKING_MESSAGE.getMessage());
        return "redirect:/auth/successful-registration";
    }

    @GetMapping("/successful-registration")
    public String SuccessfulRegistrationPage() {
        return "pages/auth-pages/successful-registration";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "pages/auth-pages/login";
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
            System.out.println(authentication);
        } catch (BadCredentialsException e) {
            throw new AppException(ErrorCode.USER_PASSWORD_INVALID, new Object());
        }
        String jwt = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
        return new AuthResponse(jwt);
    }

    /**
     * Method for logout from service
     * @param request
     * @param response
     */
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);
    }
}