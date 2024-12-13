package com.doat.ifmis_api.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class KeyGenerator implements ApplicationRunner {

    private static final String KEY_FILE_PATH = System.getProperty("user.home") + "/.secureapi/secret.key";

    static String secretKeyRead;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        byte[] keyBytes = new byte[64]; // 512-bit key
        new SecureRandom().nextBytes(keyBytes);

        // Encode the key in Base64
        String secretKey = Base64.getEncoder().encodeToString(keyBytes);

        Path path = Paths.get(KEY_FILE_PATH);

        System.out.println("========= Secret Key Generation Initiated ============");

        try {
            // Ensure directory exists
            Path directory = Paths.get(KEY_FILE_PATH).getParent();

            if(!Files.exists(directory)) {
                System.out.println("SecureApi Directory Doesn't exists -- Creating Directory");

                Files.createDirectories(directory);

                System.out.println("SecretFile Doesn't exists -- Creating Key File");

                try (PrintWriter writer = new PrintWriter(KEY_FILE_PATH)) {

                    writer.write(secretKey);
                }

                secretKeyRead = new String(Files.readAllBytes(path));

                System.out.println("Application starting with Newly created secretKey --\n"
                        + secretKeyRead
                );
            }

            else
            {
                if (!Files.exists(path)) {
                    System.out.println("Directory Exists - Secret File Doesn't -- Creating key File");

                    try (PrintWriter writer = new PrintWriter(KEY_FILE_PATH)) {

                        writer.write(secretKey);
                    }

                    secretKeyRead = new String(Files.readAllBytes(path));

                    System.out.println("Application starting with newly created secretKey --\n"
                            + secretKeyRead
                    );

                }

                else {

                    secretKeyRead = new String(Files.readAllBytes(path));

                    System.out.println("Application starting with previously created secretKey --\n"
                    + secretKeyRead
                    );

                }

            }

            System.out.println("========= Secret Key Generation Completed ============");

        } catch (IOException e) {
            throw new RuntimeException("Failed to save secret key", e);
        }

    }

    public static String getSecretKeyRead() {

        Path path = Paths.get(KEY_FILE_PATH);
        try {
            return new String(Files.readAllBytes(path));
        }
        catch(IOException e)
        {
            System.out.println("Exception Occurred with " + e.getMessage());
            return null;
        }

    }
}
