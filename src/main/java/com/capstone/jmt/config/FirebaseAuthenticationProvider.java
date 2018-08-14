package com.capstone.jmt.config;

//import com.capstone.jmt.service.MainService;
//import com.google.api.Authentication;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//
//import javax.naming.AuthenticationException;

/**
 * Created by macbookpro on 6/18/18.
 */
//public class FirebaseAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    private MainService mainService;
//
//    public boolean supports(Class<?> authentication) {
//        return (FirebaseAuthenticationToken.class.isAssignableFrom(authentication));
//    }
//
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        if (!supports(authentication.getClass())) {
//            return null;
//        }
//
//        FirebaseAuthenticationToken authenticationToken = (FirebaseAuthenticationToken) authentication;
//        UserDetails details = userService.loadUserByUsername(authenticationToken.getName());
//        if (details == null) {
//            throw new FirebaseUserNotExistsException();
//        }
//
//        authenticationToken = new FirebaseAuthenticationToken(details, authentication.getCredentials(),
//                details.getAuthorities());
//
//        return authenticationToken;
//    }
//
//}
