package com.hms16.controller;

import com.hms16.dto.Logindto;
import com.hms16.entity.AppUser;
import com.hms16.repository.AppUserRepository;
import com.hms16.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class AppUsercontroller {
    private AppUserRepository appUserRepository;
    private AppUserService appUserService;
    public AppUsercontroller(AppUserRepository appUserRepository, AppUserService appUserService) {
        this.appUserRepository = appUserRepository;
        this.appUserService = appUserService;
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody AppUser appUser
    ){
        Optional<AppUser> opuser =
                appUserRepository.findByUsername(appUser.getUsername());
        if(opuser.isPresent()){
            return new ResponseEntity<>("USername is alredy taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<AppUser> opEmail =
                appUserRepository.findByEmail(appUser.getEmail());
        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email is alredy taken",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String hashpw = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(5));
        appUser.setPassword(hashpw);
        AppUser save = appUserRepository.save(appUser);
        return new ResponseEntity<>(save,HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> verfication(
            @RequestBody Logindto logindto
    ){
        String token = appUserService.verfication(logindto);
        if(token!=null){
            return new ResponseEntity<>(token,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid username/password",HttpStatus.FORBIDDEN);
        }
    }


}
