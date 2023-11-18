package com.example.NewEcommerce.Service;

import com.example.NewEcommerce.Repository.UserRepository;
import com.example.NewEcommerce.model.CustomUserDetail;
import com.example.NewEcommerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
@Autowired
UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.finduserByEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("User name Not Found"));
        return user.map(CustomUserDetail:: new).get();
    }
}
