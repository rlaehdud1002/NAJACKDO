package com.najackdo.server.core.configuration;

import java.io.IOException;

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
	public void initialize() throws IOException {
		try {
			ClassPathResource resource = new ClassPathResource(credentials);
			FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
				.build();
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
				log.info("FirebaseApp initialization complete");
			}
		} catch (Exception e) {
			log.error("Error while initializing FirebaseApp: {}", e.getMessage());
		}
	}
}
