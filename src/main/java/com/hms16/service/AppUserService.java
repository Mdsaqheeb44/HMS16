package com.hms16.service;

import com.hms16.dto.Logindto;
import com.hms16.entity.AppUser;
import com.hms16.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService {
    private AppUserRepository appUserRepository;
    private JwtService jwtService;
    public AppUserService(AppUserRepository appUserRepository, JwtService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    public String verfication(Logindto logindto) {
        Optional<AppUser> opuser =
                appUserRepository.findByUsername(logindto.getUsername());
        if(opuser.isPresent()){
            AppUser appUser = opuser.get();
            if( BCrypt.checkpw(logindto.getPassword(), appUser.getPassword())){
                String token = jwtService.generateToken(appUser.getUsername());
                return token;
            }else {
                return null;
            }

        }else {
            return null;
        }
    }
}
