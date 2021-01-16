package com.mesutemre.service.pushnotification;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

@Service
public class AndroidPushNotificationsService {

	private static final String FIREBASE_SERVER_KEY = "AAAACO_xPzw:APA91bH2805Db0-7qEcOTGMl94BFMRH7IxpcTs-4DuJpH2IRw-FRb9xIiKa9KNX__KDuTfOgsO5Rp4CvEIvIkBfW6Bl1y2QQDDO08kzayPrSdaUbolqkxj91jOaETO0toQgnt-BxK6TE";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
	
	@Async
	  public CompletableFuture<String> send(HttpEntity<String> entity) {
	 
	    RestTemplate restTemplate = new RestTemplate();
	 
	    ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
	    interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
	    interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
	    restTemplate.setInterceptors(interceptors);
	 
	    String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
	 
	    return CompletableFuture.completedFuture(firebaseResponse);
	  }
}
