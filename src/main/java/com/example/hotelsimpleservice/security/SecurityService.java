
package com.example.hotelsimpleservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface SecurityService extends UserDetailsService {
     UserDetails loadUserByUsername(String login) throws UsernameNotFoundException;
}

