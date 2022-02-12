package de.petschi.hexDungeon.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {
    @PostConstruct
    public void initialize() {
        try {
            FileInputStream serviceAccount = new FileInputStream("./serviceAccountKey.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://hex-smacks-default-rtdb.europe-west1.firebasedatabase.app")
                    .build();
            FirebaseApp.initializeApp(options);
            System.out.println("firebase initialized.");
        } catch (IOException e) {
            System.out.println("ERROR: invalid service account credentials. See README.");
            System.out.println(e.getMessage());

            System.exit(1);
        }
    }

    public void createUser() throws ExecutionException, InterruptedException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("user@example.com")
                .setEmailVerified(false)
                .setPassword("secretPassword")
                .setPhoneNumber("+11234567890")
                .setDisplayName("John Doe")
                .setPhotoUrl("http://www.example.com/12345678/photo.png")
                .setDisabled(false);
        FirebaseAuth.getInstance().createUserAsync(request).get();
//        FirebaseAuth.getInstance().signInWithEmailAndPassword();
        System.out.println("user created");
    }

    // logs all users in blocks of 1000 and returns Collection of all user-id's
    public Collection<UUID> getUsers() throws InterruptedException, ExecutionException  {
        ListUsersPage page = FirebaseAuth.getInstance().listUsersAsync(null).get();
        Collection<UUID> users = Collections.emptyList();
        for (ExportedUserRecord user : page.iterateAll()) {
            users.add(UUID.fromString(user.getUid()));
        }
        return users;
    }

    public UserRecord getUserById(UUID uid) throws ExecutionException, InterruptedException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUserAsync(String.valueOf(uid)).get();
        System.out.println("got user " + userRecord.getUid());
        return userRecord;
    }

    public void deleteUser(UUID uid) throws InterruptedException, ExecutionException {
        FirebaseAuth.getInstance().deleteUserAsync(String.valueOf(uid)).get();
        System.out.println("Successfully deleted user.");
    }

    public void login(String name, String pwd) {
        FirebaseAuth.getInstance();
    }
}
