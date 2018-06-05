package com.capstone.jmt.controller;

import com.capstone.jmt.data.*;
import com.capstone.jmt.entity.Guidance;
import com.capstone.jmt.entity.Parent;
import com.capstone.jmt.entity.Student;
import com.capstone.jmt.entity.User;
import com.capstone.jmt.mapper.MainMapper;
import com.capstone.jmt.service.AndroidPushNotificationsService;
import com.capstone.jmt.service.MainService;
import com.capstone.jmt.service.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping(value = "/vue")
@SessionAttributes("appUser")
public class VueController {

    @Autowired
    private MainMapper mainMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password){
        HashMap<String, Object> response = new HashMap<>();

        User user = mainMapper.getUserByUsername(username);
        if(null == user){
            response.put("responseDesc","User not found.");
            response.put("responseCode", 404);
        }else{
            if(passwordEncoder.matches(password, user.getPassword())){
                response.put("responseCode", HttpStatus.OK);
                response.put("responseDesc", "Successfully Logged in.");
                response.put("user", user);
            }else{
                response.put("responseCode", 201);
                response.put("responseDesc", "Invalid Password.");
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
