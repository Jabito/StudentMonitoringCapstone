package com.capstone.jmt.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Jabito on 22/01/2018.
 */
@RestController
public class AndroidPushNotificationsService {

    private static final String FIREBASE_SERVER_KEY = "AAAA7duDZDU:APA91bFFrIeeyaVzVnSPiwENACuBnFLblO9Hr577f1L1v8rq6kzlmNoJajRodfWYfrYaSZiMbqcNSj-TLZUSelHklFJOUx_MVVJ0D6rG5FnDYlk0tL4KIAiL-0BJ0oM__YXU-LUhiuEb";
    private static final String FIREBASE_API_URL = "https://aqua-cc873.firebaseio.com";

    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();
        System.out.print(entity);

        /**
         https://fcm.googleapis.com/fcm/send
         Content-Type:application/json
         Authorization:key=FIREBASE_SERVER_KEY*/

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }
}