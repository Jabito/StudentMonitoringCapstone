package com.capstone.jmt.service;

import com.capstone.jmt.data.*;
import com.capstone.jmt.entity.*;
import com.capstone.jmt.mapper.MainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jabito on 08/08/2017.
 */
@Service("mainService")
public class MainService {

    private static final Logger logger = LoggerFactory.getLogger(MainService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MainMapper mainMapper;

    public HashMap<String, Object> loginUser(String username, String password) {
        HashMap<String, Object> response = new HashMap<>();

        User user = mainMapper.getUserByUsername(username);
        response.put("User", user);
        logger.info("user", user);
        if (null == user) {
            response.put("responseCode", HttpStatus.NOT_FOUND);
            response.put("responseDesc", "Username does not exists.");
        } else {
            if (passwordEncoder.matches(password, user.getPassword())) {
                Parent parent = mainMapper.getParent(user.getId());
                logger.info("parent", parent);
                if(null == parent){
                    response.put("responseCode", HttpStatus.NOT_FOUND);
                    response.put("responseDesc", "Parent does not exists.");
                }
                response.put("User", user);
                response.put("Parent", parent);
//                response.put("Student", mainMapper.getStudent(parent.getParentOf()));
                response.put("responseCode", HttpStatus.OK);
                response.put("responseDesc", "Login Successful.");
            } else {
                response.put("responseCode", HttpStatus.UNAUTHORIZED);
                response.put("responseDesc", "Password incorrect.");
            }
        }
        return response;
    }

    public void saveImage(PictureObject imageHolder) {
        mainMapper.saveImage(imageHolder);
    }

    public HashMap<String, Object> getStudent(String studentId) {
        HashMap<String, Object> response = new HashMap<>();
        Student student = mainMapper.getStudent(studentId);
        response.put("student", student);
        if (null == student) {
            response.put("responseCode", HttpStatus.NOT_FOUND);
            response.put("responseDesc", "Student does not exists.");
        } else {
            response.put("responseCode", HttpStatus.OK);
            response.put("responseDesc", "Student Found.");
        }

        return response;
    }

    public HashMap<String, Object> getGuidance(String id) {
        HashMap<String, Object> response = new HashMap<>();
        Guidance guidance = mainMapper.getGuidance(id);
        response.put("guidance", guidance);
        if (null == guidance) {
            response.put("responseCode", HttpStatus.NOT_FOUND);
            response.put("responseDesc", "Guidance does not exists.");
        } else {
            response.put("responseCode", HttpStatus.OK);
            response.put("responseDesc", "Guidance Found.");
        }

        return response;
    }

    public HashMap<String, Object> getParent(String id) {
        HashMap<String, Object> response = new HashMap<>();
        Parent parent = mainMapper.getParent(id);
        response.put("parent", parent);
        if (null == parent) {
            response.put("responseCode", HttpStatus.NOT_FOUND);
            response.put("responseDesc", "Parent does not exists.");
        } else {
            response.put("responseCode", HttpStatus.OK);
            response.put("responseDesc", "Parent Found.");
        }

        return response;
    }

    public HashMap<String, Object> getEmergencyContact(String id) {
        HashMap<String, Object> response = new HashMap<>();
        EmergencyContact eContact = mainMapper.getEmergencyContact(id);
        response.put("emergencyContact", eContact);
        if (null == eContact) {
            response.put("responseCode", HttpStatus.NOT_FOUND);
            response.put("responseDesc", "Emergency Contact does not exists.");
        } else {
            response.put("responseCode", HttpStatus.OK);
            response.put("responseDesc", "Emergency Contact Found.");
        }

        return response;
    }


    public HashMap<String, Object> getLastTapEntry(String studentId) {
        HashMap<String, Object> response = new HashMap<>();
        TapLog tapLog = mainMapper.getLastTapDetailsByStudentId(studentId);
        if (null != tapLog) {
            response.put("tapDetails", tapLog);
            response.put("responseCode", 200);
            response.put("responseDesc", "Last tap entry retrieved.");
        } else {
            response.put("tapDetails", new TapLog());
            response.put("responseCode", 404);
            response.put("responseDesc", "No logs yet.");
        }
        return response;
    }

    public HashMap<String, Object> getTapLogOfStudent(String studentId) {
        HashMap<String, Object> response = new HashMap<>();
        List<TapLog> tapLog = mainMapper.getTapListDetailsByStudentId(studentId);
        if (null != tapLog) {
            response.put("tapListDetails", tapLog);
            response.put("responseCode", 200);
            response.put("responseDesc", "Last tap entry retrieved.");
        } else {
            response.put("tapListDetails", new ArrayList<>());
            response.put("responseCode", 404);
            response.put("responseDesc", "No logs yet.");
        }
        return response;
    }

    public HashMap<String, Object> updateStudentInfo(Student student) {
        HashMap<String, Object> response = new HashMap<>();
        Student existingStudent = mainMapper.getStudent(student.getId());
        if (null != existingStudent) {
            mainMapper.updateStudent(student);
        } else {
            response.put("responseCode", 404);
            response.put("responseDesc", "Failed to update student.");
        }
        return response;
    }

    public HashMap<String, Object> addStudent(Student student) {
        HashMap<String, Object> response = new HashMap<>();
        student.setId("SID" + String.valueOf(mainMapper.getLastId(4)));
        mainMapper.incrementId(4);
        mainMapper.addStudent(student);
        response.put("responseCode", 200);
        response.put("responseDesc", "Successfully added student.");
        return response;
    }

    public HashMap<String, Object> addParent(Parent parent) {
        HashMap<String, Object> response = new HashMap<>();
        parent.setId("PID" + mainMapper.getLastId(2));
        mainMapper.incrementId(2);
        mainMapper.addParent(parent);
        response.put("responseCode", 200);
        response.put("responseDesc", "Successfully added parent.");

        return response;
    }

    public HashMap<String, Object> addEmergencyContact(EmergencyContact eContact) {
        HashMap<String, Object> response = new HashMap<>();
        eContact.setId("EID" + mainMapper.getLastId(3));
        mainMapper.incrementId(3);
        mainMapper.addEmergencyContact(eContact);
        response.put("responseCode", 200);
        response.put("responseDesc", "Successfully added emergency contact.");

        return response;
    }

    public User getUser(String username) {
        return mainMapper.getUserByUsername(username);
    }

    public HashMap<String, Object> deleteStudentById(String id) {
        HashMap<String, Object> response = new HashMap<>();
        mainMapper.deleteStudentById(id);
        return response;
    }

    public HashMap<String, Object> postAnnouncement(MessageJson mj) {
        HashMap<String, Object> response = new HashMap<>();
        String forInsert = "";
        for (int i = 0; i < mj.getParentIds().length; i++) {
            if (i == 0)
                forInsert += mj.getParentIds()[i];
            else
                forInsert += "," + mj.getParentIds()[i];
        }
        mj.setMessageTarget(forInsert);
        mainMapper.postAnnouncement(mj);
        response.put("responseCode", 200);
        response.put("responseDesc", "Announcement Posted.");

        return response;
    }


    public HashMap<String, Object> processRfidTap(String rfid) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("responseCode", 200);
        response.put("responseDesc", "Success.");
        return response;
    }

    public void addUser(AddUserJson userJson) {
        User user = new User(userJson);
        Guidance guidance = mainMapper.getGuidance(userJson.getReferenceId());
        Parent parent = mainMapper.getParent(user.getReferenceId());
        user.setId(guidance != null ? "GID" + String.valueOf(mainMapper.getLastId(1)) :
                parent != null ? "PID" + String.valueOf(mainMapper.getLastId(2)) :
                        "AID" + String.valueOf(mainMapper.getLastId(0)));
        user.setReferenceId("ADMIN");
        if (guidance != null) {
            user.setUserTypeId(1);
            mainMapper.incrementId(1);
        } else if (parent != null) {
            user.setUserTypeId(2);
            mainMapper.incrementId(2);
        } else {
            user.setUserTypeId(0);
            mainMapper.incrementId(0);
        }
        user.setPassword(passwordEncoder.encode(userJson.getPassword()));
        mainMapper.addUser(user);
    }

    public List<RefGradeLevel> getGradeLevelList() {
        return mainMapper.getGradeLevelList();
    }

    public List<RefSection> getSectionList(int gradeLvlId) {
        return mainMapper.getSectionList(gradeLvlId);
    }

    public HashMap<String, Object> addGuidance(Guidance guidance) {
        HashMap<String, Object> response = new HashMap<>();
        guidance.setId("GID" + mainMapper.getLastId(1));
        mainMapper.incrementId(1);
        mainMapper.addTeacher(guidance);
        response.put("responseCode", 200);
        response.put("responseDesc", "Successfully Added Guidance.");
        return response;
    }

    public Student getStudentByRfidIn(){

        return mainMapper.getStudIn();
    }

    public Student getStudentByRfidOut(){
        return mainMapper.getStudOut();
    }

    public void tapStudent(String rfid){
        TapLog tapLog = new TapLog();
        tapLog.setRfid(rfid);
        tapLog.setLogType("IN");
        mainMapper.processRfidTap(tapLog);
    }

    public HashMap<String, Object> getUserById(String id) {
        HashMap<String, Object> response = new HashMap<>();
        User user = mainMapper.getUserById(id);
        response.put("user", user);
        if (null == user) {
            response.put("responseCode", HttpStatus.NOT_FOUND);
            response.put("responseDesc", "Cannot find user. Invalid Id.");
        } else {
            response.put("responseCode", HttpStatus.OK);
            response.put("responseDesc", "Successfully retrieved user.");
        }
        return response;
    }

    public HashMap<String, Object> getAnnouncements(String parentId) {
        HashMap<String, Object> response = new HashMap<>();
        List<MessageJson> announcements = mainMapper.getAnnouncementsByParentId("%" + parentId + "%");
        response.put("announcements", announcements);
        response.put("responseCode", 200);
        response.put("responseDesc", "Successfully retrieved list.");
        return response;
    }

    public HashMap<String, Object> getFilteredParentsBySection(String section) {
        HashMap<String, Object> response = new HashMap<>();
        List<Parent> parents = mainMapper.getFilteredParentsBySection(section);
        response.put("parents", parents);
        response.put("responseCode", 200);
        response.put("responseDesc", "Successfully retrieved list.");
        return response;
    }

    public HashMap<String, Object> getParentsByGradeLevelId(Integer gradeLevelId) {
        HashMap<String, Object> response = new HashMap<>();
        List<Parent> parents = mainMapper.getParentsByGradeLevelId(gradeLevelId);
        response.put("parents", parents);
        response.put("responseCode", 200);
        response.put("responseDesc", "Successfully retrieved list.");
        return response;
    }

    public HashMap<String, Object> toggleSMS(String parentId, boolean mode) {
        HashMap<String, Object> response = new HashMap<>();
        mainMapper.toggleSMS(parentId, mode);
        response.put("responseCode", 200);
        response.put("responseDesc", "Successfully toggled SMS Notif.");
        return response;
    }

    public List<Student> getAllStudents() {
       return mainMapper.getAllStudents();
    }

    public void addGuidanceRecord(GuidanceRecord gr){
        gr.setId("GR" + mainMapper.getLastId(5));
        mainMapper.addGuidanceRecord(gr);
    }

    public List<GuidanceRecord> getGuidanceRecord(String studentId){
        return mainMapper.getGuidanceRecord(studentId);
    }

    public void uploadImageById(MultipartFile file, String username){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        String id = sdf.format(new Date());
//        String contentType = po.getContentType();
//        PictureObject imageHolder = new PictureObject();
//        imageHolder.setStudentId(po.getStudentId());
//        imageHolder.setFileId(id);
//        imageHolder.setContentType(contentType);
//        imageHolder.setOriginalFileName(po.getOriginalFileName());
//        imageHolder.setFileNameNoSuffix(po.getOriginalFileName().substring(0, po.getOriginalFileName().indexOf(".")));
//        imageHolder.setFileSuffix(po.getOriginalFileName().substring(po.getOriginalFileName().indexOf(".")));
//
//        HashMap<String, Object> response = new HashMap<>();
//
//        if (!file.isEmpty()) {
//            try {
//                imageHolder.setContent(file.getBytes());
//                maceService.saveImage(imageHolder);
//                VerifiedMember verifiedMember = maceService.getLatestVerifiedMember(memCode);
//
//                response.put("responseCode", 200);
//                response.put("responseDesc", "Success");
//                return new ResponseEntity<>(response, HttpStatus.OK);
//
//            } catch (Exception e) {
//                response.put("responseCode", 500);
//                response.put("responseDesc", "Internal Server Error");
//                response.put("error", e.getMessage());
//                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        } else {
//            response.put("responseCode", 204);
//            response.put("responseDesc", "File is empty");
//            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
//        }
    }

    public PictureObject retrieveImage(String fileId) {
        return mainMapper.retrieveImage(fileId);
    }
}
