package de.petschi.hexDungeon.controller;

import com.google.firebase.auth.FirebaseAuth;
import de.petschi.hexDungeon.service.FirebaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private final FirebaseService firebaseService;

    @Autowired
    public AuthController() {
        this.firebaseService = new FirebaseService();
    }

    @PostMapping
    public void login(String name, String pwd) {
        this.firebaseService.login(name, pwd);
    }
}
