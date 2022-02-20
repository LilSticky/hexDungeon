package de.petschi.hexDungeon.controller;

import com.google.firebase.auth.FirebaseAuth;
import de.petschi.hexDungeon.service.FirebaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private final FirebaseService firebaseService;

    @Autowired
    public AuthController() {
        this.firebaseService = new FirebaseService();
    }

    @GetMapping
    public Collection<String> getUsers() throws ExecutionException, InterruptedException { return this.firebaseService.getUsers(); }

    @PostMapping
    public void login(String name, String pwd) { System.out.println(name); }
}
