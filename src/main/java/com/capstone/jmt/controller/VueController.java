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
import java.util.List;

@Controller
@RequestMapping(value = "/vue")
@SessionAttributes("appUser")
@CrossOrigin
public class VueController {

    @Autowired
    private MainService mainService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        HashMap<String, Object> response = new HashMap<>();

        User user = mainService.getUser(username);
        if (null == user) {
            response.put("responseDesc", "User not found.");
            response.put("responseCode", 404);
        } else {
            if (passwordEncoder.matches(password, user.getPassword())) {
                response.put("responseCode", HttpStatus.OK);
                response.put("responseDesc", "Successfully Logged in.");
                response.put("user", user);
            } else {
                response.put("responseCode", 201);
                response.put("responseDesc", "Invalid Password.");
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAttendanceLogsDetails", method = RequestMethod.GET)
    public ResponseEntity<?> getAttendanceLogsDetails(@RequestParam(value = "studId") String studId) {
        return new ResponseEntity<>(mainService.getTapLogOfStudent(studId).get("tapListDetails"), HttpStatus.OK);
    }

    @RequestMapping(value = "/getWeeklyAttendance", method = RequestMethod.GET)
    public ResponseEntity<?> getWeeklyAttendance() {
        return new ResponseEntity<>(mainService.getWeeklyAttendance(), HttpStatus.OK);
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public ResponseEntity<?> addStudent(@RequestBody AddStudentJson studentJson) {
        HashMap<String, Object> response = new HashMap<>();
        Student stud = new Student(studentJson);
        mainService.addStudent(stud);
        response.put("responseCode", 200);
        response.put("responseDesc", "Successfully saved student.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/getGradeLevels", method = RequestMethod.GET)
    public ResponseEntity<?> getGradeLevels() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("gradeLvls", mainService.getGradeLevelList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
