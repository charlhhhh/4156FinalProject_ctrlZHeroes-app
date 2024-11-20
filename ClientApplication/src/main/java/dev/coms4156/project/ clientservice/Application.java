package dev.coms4156.project.clientservice;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        initializeFirebase();
        SpringApplication.run(Application.class, args);
    }

    private static void initializeFirebase() {
        try {
            FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/serviceAccountKey.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            System.err.println("Failed to initialize Firebase: " + e.getMessage());
        }
    }
}
