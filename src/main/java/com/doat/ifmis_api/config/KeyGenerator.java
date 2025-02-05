package com.doat.ifmis_api.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(KeyGenerator.class);
    private static final String BASE_LOC_NAME = "user.home";
    private static final String KEY_FILE_PATH = System.getProperty(BASE_LOC_NAME) + "/.secureapi/secret.key";

    @Override
    public void run(ApplicationArguments args) throws Exception {

        byte[] keyBytes = new byte[64]; // 512-bit key
        new SecureRandom().nextBytes(keyBytes);

        // Encode the key in Base64
        String secretKey = Base64.getEncoder().encodeToString(keyBytes);

        Path path = Paths.get(KEY_FILE_PATH);

        logger.info("========= Secret Key Generation Initiated ============");

        try {
            // Ensure directory exists
            Path directory = path.getParent();

            if (!Files.exists(directory)) {
                logger.warn("SecureApi Directory {} Doesn't exists -- Creating Directory", System.getProperty(BASE_LOC_NAME));

                Files.createDirectories(directory);

                logger.warn("SecretFile Doesn't exists -- Creating Key File");

                try (PrintWriter writer = new PrintWriter(KEY_FILE_PATH)) {

                    writer.write(secretKey);
                }

                logger.info("Application starting with Newly created secretKey");
            } else {
                if (!Files.exists(path)) {
                    logger.warn("Directory {} Exists - Secret File Doesn't -- Creating key File", System.getProperty(BASE_LOC_NAME));

                    try (PrintWriter writer = new PrintWriter(KEY_FILE_PATH)) {
                        writer.write(secretKey);
                    }

                    logger.info("Application starting with newly created secretKey");

                } else {
                    logger.info("Application starting with previously created secretKey");

                }

            }

            logger.info("========= Secret Key Generation Completed ============");

        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to save secret key", e);
        }

    }

}
