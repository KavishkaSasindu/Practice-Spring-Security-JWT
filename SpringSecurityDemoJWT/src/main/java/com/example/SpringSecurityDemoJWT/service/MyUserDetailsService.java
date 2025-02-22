package com.example.SpringSecurityDemoJWT.service;

import com.example.SpringSecurityDemoJWT.model.UserModel;
import com.example.SpringSecurityDemoJWT.model.UserPrincipal;
import com.example.SpringSecurityDemoJWT.repo.UserRepo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Data
@Service
@NoArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private UserRepo userRepo;

    @Autowired
    public MyUserDetailsService(UserRepo userrepo) {
        this.userRepo = userrepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel user = userRepo.findByUsername(username);

        if(user == null) {
            System.out.println("user not found");
            throw new UsernameNotFoundException(username+" not found");
        }

        return new UserPrincipal(user);
    }
}
