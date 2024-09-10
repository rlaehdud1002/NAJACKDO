package com.najackdo.server.core.configuration;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FCMInitialize {

	@Value("${fcm.certification}")
	private String credentials;

	@PostConstruct
	public void initialize() {
		try {
			initializeFirebaseApp();
		} catch (IOException e) {
			log.error("IOException while initializing FirebaseApp: {}", e.getMessage());
		} catch (Exception e) {
			log.error("Unexpected error while initializing FirebaseApp: {}", e.getMessage());
		}
	}

	private void initializeFirebaseApp() throws IOException {
		ClassPathResource resource = new ClassPathResource(credentials);

		try (InputStream inputStream = resource.getInputStream()) {
			FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(inputStream))
				.build();

			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
				log.info("FirebaseApp initialization complete");
			}
		}
	}
}
