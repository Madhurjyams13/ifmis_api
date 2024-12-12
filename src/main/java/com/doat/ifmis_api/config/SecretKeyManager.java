package com.doat.ifmis_api.config;


import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class SecretKeyManager {

    private static final String KEY_FILE_PATH = System.getProperty("user.home") + "/.secureapi/secret.key";

    public static void saveSecretKey(String secretKey) {
        try {
            // Ensure directory exists
            Path directory = Paths.get(KEY_FILE_PATH).getParent();
            Files.createDirectories(directory);

            // Write secret key to file
            try (PrintWriter writer = new PrintWriter(KEY_FILE_PATH)) {
                System.out.println(secretKey);
                writer.write(secretKey);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save secret key", e);
        }
    }

    public static String loadSecretKey() {
        try {
            Path path = Paths.get(KEY_FILE_PATH);

            // Check if file exists
            if (!Files.exists(path)) {
                throw new FileNotFoundException("Secret key file not found. Generate a key first.");
            }

            // Read secret key from file
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load secret key", e);
        }
    }

    public static boolean secretKeyExists() {
        return Files.exists(Paths.get(KEY_FILE_PATH));
    }

}
