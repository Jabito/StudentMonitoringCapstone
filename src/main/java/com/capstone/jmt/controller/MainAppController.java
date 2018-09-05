package com.capstone.jmt.controller;

import com.capstone.jmt.data.*;
import com.capstone.jmt.entity.Guidance;
import com.capstone.jmt.entity.Parent;
import com.capstone.jmt.entity.Student;
import com.capstone.jmt.entity.User;
import com.capstone.jmt.service.EmailService;
import com.capstone.jmt.service.MainService;
import com.capstone.jmt.service.StorageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Jabito on 08/08/2017.
 */
@Controller
@RequestMapping(value = "/app/")
@SessionAttributes("appUSer")
public class MainAppController {

    @Autowired
    private MainService mainService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "toggleSMS", method = RequestMethod.POST)
    public ResponseEntity<?> toggleSMS(@RequestParam("parentId") String parentId, @RequestParam("mode") boolean mode){
        return new ResponseEntity<>(mainService.toggleSMS(parentId, mode), HttpStatus.OK);
    }

    @RequestMapping(value = "processRfidTap", method = RequestMethod.POST)
    public ResponseEntity<?> processRfidTap(@RequestParam("rfid") String rfid) {
        HashMap<String, Object> response = new HashMap<>();
        response.putAll(mainService.processRfidTap(rfid));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "loginUser", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
        HashMap<String, Object> response = new HashMap<>();
        response.putAll(mainService.loginUser(username, password));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "downloadPicture", method = RequestMethod.GET)
    public ResponseEntity<?> downloadPicture(@RequestParam("userId") String userId) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Resource file = storageService.loadFile(null == userId? mainService.retrieveImage(userId).getOriginalFileName(): "image.png");
            InputStream in = file.getInputStream();
            byte[] media = IOUtils.toByteArray(in);

            response.put("imageBase64", media);
            response.put("responseDesc", "Success to retrieve image.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(IOException e){
            response.put("responseDesc", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@RequestParam String id) {
        HashMap<String, Object> response = new HashMap<>();
        response.putAll(mainService.getUserById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "getAllStudents", method = RequestMethod.GET)
    public ResponseEntity<?> getAllStudents() {
        return new ResponseEntity<>(mainService.getAllStudents(), HttpStatus.OK);
    }

    @RequestMapping(value = "getStudent", method = RequestMethod.GET)
    public ResponseEntity<?> getStudent(@RequestParam String studentId) {
        HashMap<String, Object> response = new HashMap<>();
        response.putAll(mainService.getStudent(studentId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "getGuidance", method = RequestMethod.GET)
    public ResponseEntity<?> getGuidance(@RequestParam String id) {
        HashMap<String, Object> response = new HashMap<>();
        response.putAll(mainService.getGuidance(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "getParent", method = RequestMethod.GET)
    public ResponseEntity<?> getParent(@RequestParam String id) {
        HashMap<String, Object> response = new HashMap<>();
        response.putAll(mainService.getParent(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "getFilteredParentsBySection", method =  RequestMethod.GET)
    public ResponseEntity<?> getFilteredParentsBySection(@RequestParam("section") String section) {
        HashMap<String, Object> response = new HashMap<>();
        response.putAll(mainService.getFilteredParentsBySection(section));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value="getParentsByGradeLevelId", method = RequestMethod.GET)
    public ResponseEntity<?> getParentsByGradeLevelId(@RequestParam("gradeLevelId") Integer gradeLevelId) {
        HashMap<String, Object> response = new HashMap<>();
        response.putAll(mainService.getParentsByGradeLevelId(gradeLevelId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "getEmergencyContact", method = RequestMethod.GET)
    public ResponseEntity<?> getEmergencyContact(@RequestParam String id) {
        HashMap<String, Object> response = new HashMap<>();
        response.putAll(mainService.getEmergencyContact(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "getTapLogOfStudent", method = RequestMethod.GET)
    public ResponseEntity<?> getTapLogOfStudent(@RequestParam String studentId) {
        HashMap<String, Object> response = new HashMap<>();
        response.putAll(mainService.getTapLogOfStudent(studentId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "getLastTapEntry", method = RequestMethod.GET)
    public ResponseEntity<?> getLastTapEntry(@RequestParam String studentId) {
        HashMap<String, Object> response = new HashMap<>();
        response.putAll(mainService.getLastTapEntry(studentId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "editStudentInfo", method = RequestMethod.POST)
    public ResponseEntity<?> editStudentInfo(@RequestParam Student student) {
        HashMap<String, Object> response = new HashMap<>();
        response.putAll(mainService.updateStudentInfo(student));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "updateTempPassword", method = RequestMethod.POST)
    public ResponseEntity<?> editStudentInfo(@RequestParam String email) {
        HashMap<String, Object> response = new HashMap<>();
        String s = mainService.updatePassword(email);

        if(s != null) {

            emailService.sendPasswordReset(email);

            response.put("responseCode", 200);
            response.put("responseDesc", "success updated password");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "addGuidance", method = RequestMethod.POST)
    public ResponseEntity<?> addGuidance(@RequestBody AddGuidanceJson teacherJson) {
        HashMap<String, Object> response = new HashMap<>();
        User user = mainService.getUser(teacherJson.getAppUsername());
        if (user.getUserTypeId() == 0) {
            Guidance guidance = new Guidance(teacherJson);
            response.putAll(mainService.addGuidance(guidance));
        } else {
            response.put("responseCode", 404);
            response.put("respnoseDesc", "Unauthorized request. User does not have admin status.");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "addStudent", method = RequestMethod.POST)
    public ResponseEntity<?> addStudent(@RequestBody Student student, @RequestParam String appUsername) {
        HashMap<String, Object> response = new HashMap<>();
        User teacher = mainService.getUser(appUsername);
        if (teacher.getUserTypeId() == 0) {
            response.putAll(mainService.addStudent(student));
        } else {
            response.put("responseCode", 404);
            response.put("respnoseDesc", "Unauthorized request. User does not have admin status.");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "deleteStudent", method = RequestMethod.POST)
    public ResponseEntity<?> deleteStudent(@RequestParam String studentId, @RequestParam String appUsername) {
        HashMap<String, Object> response = new HashMap<>();
        User teacher = mainService.getUser(appUsername);
        if (teacher.getUserTypeId() == 0) {
            response.putAll(mainService.deleteStudentById(studentId));
        } else {
            response.put("responseCode", 404);
            response.put("respnoseDesc", "Unauthorized request. User does not have admin status.");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody AddUserJson user) {
        HashMap<String, Object> response = new HashMap<>();
        User admin = mainService.getUser(user.getAppUsername());
        System.out.println(user.getAppUsername());
        if (null != admin) {
                if(0 == admin.getUserTypeId()) {
                User teacher = mainService.getUser(user.getUsername());
                if (null != teacher) {
                    response.put("responseCode", 201);
                    response.put("responseDesc", "Username already taken.");
                } else {
                    response.put("responseCode", 200);
                    response.put("responseDesc", "Successfully created User.");
                    User obj = new User(user);
                    mainService.addUser(obj);
                }
            }else{
                response.put("responseCode", HttpStatus.UNAUTHORIZED);
                response.put("responseDesc", "Unauthorized user.");
            }
        } else {
            response.put("responseCode", 404);
            response.put("responseDesc", "Admin user ID not found.");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value="addParent", method = RequestMethod.POST)
    public ResponseEntity<?> addParent(@RequestBody Parent par){
        return new ResponseEntity<>(mainService.addParent(par), HttpStatus.OK);
    }

    @RequestMapping(value = "postAnnouncement", method = RequestMethod.POST)
    public ResponseEntity<?> postAnnouncement(@RequestBody MessageJson mj) {
        return new ResponseEntity<>(mainService.postAnnouncement(mj), HttpStatus.OK);
    }

    @RequestMapping(value = "getAnnouncements", method = RequestMethod.GET)
    public ResponseEntity<?> getAnnouncements(@RequestParam String userId){
        return new ResponseEntity<>(mainService.getAnnouncements(userId), HttpStatus.OK);
    }
}
