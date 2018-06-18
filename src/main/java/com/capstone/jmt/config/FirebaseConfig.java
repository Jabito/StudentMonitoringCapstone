package com.capstone.jmt.config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * Created by macbookpro on 6/18/18.
 */
@Configuration
public class FirebaseConfig {

    @Bean
    public DatabaseReference firebaseDatabse() {
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        return firebase;
    }

    @Value("${rs.pscode.firebase.database.url}")
    private String databaseUrl;

    @Value("${rs.pscode.firebase.config.path}")
    private String configPath;

    @PostConstruct
    public void init() {

        /**
         * https://firebase.google.com/docs/server/setup
         *
         * Create service account , download json
         */
        InputStream inputStream = FirebaseConfig.class.getClassLoader().getResourceAsStream(configPath);

        FirebaseOptions options = new FirebaseOptions.Builder().setServiceAccount(inputStream)
                .setDatabaseUrl(databaseUrl).build();
        FirebaseApp.initializeApp(options);

    }
}
