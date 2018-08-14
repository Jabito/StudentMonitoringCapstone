package com.capstone.jmt.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.Tasks;
import io.opencensus.internal.StringUtil;

/**
 * Created by macbookpro on 6/18/18.
 */

public class FirebaseParser {


//    public FirebaseTokenHolder parseToken(String idToken) {
//        if (StringUtil.isBlank(idToken)) {
//            throw new IllegalArgumentException("FirebaseTokenBlank");
//        }
//        try {
//            Task<FirebaseToken> authTask = FirebaseAuth.getInstance().verifyIdToken(idToken);
//
//            Tasks.await(authTask);
//
//            return new FirebaseTokenHolder(authTask.getResult());
//        } catch (Exception e) {
//            throw new FirebaseTokenInvalidException(e.getMessage());
//        }
//    }
}