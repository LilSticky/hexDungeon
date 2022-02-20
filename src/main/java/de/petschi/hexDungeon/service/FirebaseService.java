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
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FirebaseService {
    @PostConstruct
    public void initialize() {
        try {
            FileInputStream serviceAccount = new FileInputStream("./service-account.json");
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

//  now fancy with streams
    /**
     * returns all users in the database
     * @return Collection<UUID> the users
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Collection<String> getUsers() throws InterruptedException, ExecutionException  {
        ListUsersPage page = FirebaseAuth.getInstance().listUsersAsync(null).get();
        Spliterator<ExportedUserRecord> userRecords = page.iterateAll().spliterator();
        return StreamSupport.stream(userRecords, false)
                .map(UserRecord::getUid)
                .collect(Collectors.toList());
    }

//  Java Streams filter example
    public String getFakeUser(String uid) throws ExecutionException, InterruptedException {
        return getUsers().stream()
                .filter(user -> user.equals(uid))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public UserRecord getUserById(String uid) throws ExecutionException, InterruptedException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUserAsync(uid).get();
        System.out.println("got user " + userRecord.getUid());
        return userRecord;
    }

    public void deleteUser(String uid) throws InterruptedException, ExecutionException {
        FirebaseAuth.getInstance().deleteUserAsync(uid).get();
        System.out.println("Successfully deleted user.");
    }
}
