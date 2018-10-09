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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
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
            if (passwordEncoder.matches(password,user.getPassword())) {

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


    public String updatePassword(String email) {
        String tempPassword = "Password!1";
        String encodedPassword = passwordEncoder.encode(tempPassword);
        System.out.println("ENCODED PASSWORD!: " + encodedPassword);
        mainMapper.updatePassword(email,encodedPassword);
        return tempPassword;
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
            System.out.println("UPDATED");
            mainMapper.updateStudent(student);
            System.out.println("UPDATED SUCCESSFULLY");
        } else {
            response.put("responseCode", 404);
            response.put("responseDesc", "Failed to update student.");
        }
        return response;
    }

    public HashMap<String, Object> updateParent(Parent parent) {
        HashMap<String, Object> response = new HashMap<>();
        Parent existingStudent = mainMapper.getParent(parent.getId());
        if (null != existingStudent) {
            System.out.println("UPDATED");
            mainMapper.updateParent(parent);
            System.out.println("UPDATED SUCCESSFULLY");
        } else {
            response.put("responseCode", 404);
            response.put("responseDesc", "Failed to update parent.");
        }
        return response;
    }

    public HashMap<String, Object> updateUser(User user) {
        HashMap<String, Object> response = new HashMap<>();
        User existingStudent = mainMapper.getUserById(user.getId());
        if (null != existingStudent) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            mainMapper.updateUser(user);
        } else {
            response.put("responseCode", 404);
            response.put("responseDesc", "Failed to update user.");
        }
        return response;
    }


    public HashMap<String, Object> updateGuidance(Guidance guidance) {
        HashMap<String, Object> response = new HashMap<>();
        Guidance existingStudent = mainMapper.getGuidance(guidance.getId());
        if (null != existingStudent) {
            System.out.println("UPDATED");
            mainMapper.updateGuidance(guidance);
            System.out.println("UPDATED SUCCESSFULLY");
        } else {
            response.put("responseCode", 404);
            response.put("responseDesc", "Failed to update guidance.");
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

    public HashMap<String, Object> archiveAllStudents(String date){
        if(null == date || date.equals(""))
            date = "2018";
        HashMap<String, Object> response = new HashMap<>();
        System.out.println("ARCHIVING ALL STUDENTS.");
        mainMapper.archiveAllStudents(date);
        return response;
    }

    public HashMap<String, Object> promoteStudents(String date){
        if(null == date || date.equals(""))
            date = "2018";
        HashMap<String, Object> response = new HashMap<>();
        mainMapper.promoteStudents(date);
        return response;
    }

    public HashMap<String, Object> unArchiveAllStudents(String date){
        HashMap<String, Object> response = new HashMap<>();
        System.out.println("RECOVERING ALL STUDENTS.");

        mainMapper.unArchiveAllStudents(date);
        return response;
    }

    public HashMap<String, Object> unDeleteStudent(String id){
        HashMap<String, Object> response = new HashMap<>();
        mainMapper.unDeleteStudent(id);
        return response;
    }

    public HashMap<String, Object> deleteParent(String id) {
        HashMap<String, Object> response = new HashMap<>();
        mainMapper.deleteParentById(id);
        return response;
    }

    public HashMap<String, Object> deleteUser(String id) {
        HashMap<String, Object> response = new HashMap<>();
        mainMapper.deleteUserById(id);
        return response;
    }

    public HashMap<String, Object> deleteGuidance(String id) {
        HashMap<String, Object> response = new HashMap<>();
        mainMapper.deleteGuianceById(id);
        return response;
    }

    public HashMap<String, Object> postAnnouncement(MessageJson mj) {
        HashMap<String, Object> response = new HashMap<>();

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

    public void addUser(User user) {
        Guidance guidance = mainMapper.getGuidance(user.getReferenceId());
        Parent parent = mainMapper.getParent(user.getReferenceId());
        user.setId(guidance != null ? guidance.getId() :
                parent != null ? parent.getId():
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public User getUserId(String id) {
        return mainMapper.getUserById(id);
    }

    public Guidance getGuidanceById(String id) {
        return mainMapper.getGuidance(id);
    }


    public HashMap<String, Object> getAnnouncements(String userId) {
        HashMap<String, Object> response = new HashMap<>();
        List<MessageJson> announcements = mainMapper.getAnnouncementsByUserId(userId);
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

    public List<Student> getAllArchivedStudents() {
        return mainMapper.getAllArchivedStudents();
    }

    public String addGuidanceRecord(GuidanceRecord gr){
        gr.setId("GR" + mainMapper.getLastId(5));
        mainMapper.incrementId(5);
        mainMapper.addGuidanceRecord(gr);
        return gr.getId();
    }

    public List<GuidanceRecord> getGuidanceRecord(String studentId){
        return mainMapper.getGuidanceRecord(studentId);
    }

    public List<GuidanceRecord> getGuidanceRecordList(){
        List<GuidanceRecord> list = mainMapper.getGuidanceRecordList();
        for (GuidanceRecord guidanceRecord : list) {
            guidanceRecord.setGradeAndSection((null != guidanceRecord.getGradeLevel()? guidanceRecord.getGradeLevel():"")
                    + " - " + (null != guidanceRecord.getSectionName()? guidanceRecord.getSectionName():""));
        }
        return list;
    }

    public List<GuidanceRecord> getGuidanceRecordListWithParams(Date dateF, Date dateT, String searchString){
        System.out.println(dateF);
        System.out.println(dateT);
        List<GuidanceRecord> list = mainMapper.getGuidanceRecordListWithParams(dateF, dateT, "%" + searchString + "%", searchString);
        for (GuidanceRecord guidanceRecord : list) {
            guidanceRecord.setGradeAndSection((null != guidanceRecord.getGradeLevel()? guidanceRecord.getGradeLevel():"")
                    + " - " + (null != guidanceRecord.getSectionName()? guidanceRecord.getSectionName():""));
        }
        return list;
    }

    public PictureObject retrieveImage(String fileId) {
        return mainMapper.retrieveImage(fileId);
    }

    public List<Student> getStudentList(int date) {
        return mainMapper.getStudentList(date);
    }

    public List<Student> getArchivedStudentList(int date) {
        return mainMapper.getArchivedStudentList(date);
    }

    public List<Parent> getParentList() {
        return mainMapper.getParentList();
    }

    public Student getStudentById(String id) {
        return mainMapper.getStudentById(id);
    }

    public Student findStudentByFnameLname(String fname, String lname){
        return mainMapper.findStudentByFnameLname(fname, lname);
    }

    public Parent getParentById(String id) {
        return mainMapper.getParent(id);
    }

    public List<Student> getStudentsBySearch(String searchString){
        return mainMapper.getStudentsBySearchString("%"+ searchString + "%");
    }

    public List<RefUserType> getUserType() {
        return mainMapper.getUserType();
    }

    public String getGradelevelStringById(Integer gradeLvlId) {
        return mainMapper.getGradelevelStringById(gradeLvlId);
    }

    public List<AttendanceRow> getWeeklyAttendance(){
        return mainMapper.getWeeklyAttendance();
    }

    public List<UserList> getUsersByUserTypeId(int userTypeId) {
        return mainMapper.getUsersByUserTypeId(userTypeId);
    }

    public Student getStudentByRfid(String rfid) {
        return mainMapper.getStudentByRfid(rfid);
    }

    public List<TapLog> getTapAllTopLogs() {
        return mainMapper.getTapAllTopLogs();
    }

    public List<TapLog> getFilteredTapLogs(Date dateFrom, Date dateTo, String searchString){
        return mainMapper.getFilteredTapLogs(dateFrom, dateTo, "%"+searchString+"%");
    }

    public Parent getParentByStudentId(String studentId) {
        return mainMapper.getParentByStudentId(studentId);
    }

    public void sendFirebase(String message) {

    }

    public void sendSMSLogin(String id) {

    }

    public String getLastTapDate(String mode) {
        return mainMapper.getLastTapDate(mode);
    }

    public List<TapLog> getTapLogsByParentId(String id) {
        return mainMapper.getTapLogsByParentId(id);
    }

    public List<TapLog> getFilteredTapLogsByParentId(Date dateFrom, Date dateTo, String searchString, String id) {
        return mainMapper.getFilteredTapLogsByParentId(dateFrom, dateTo, "%"+searchString+"%", id);
    }

    public List<RefSection> getSectionListByGradeLevel(String gradeLevel) {
        return mainMapper.getSectionListByGradeLevel(gradeLevel);
    }

    public int getGradeLvlIdByGradeLevel(String gradeLevel) {
        return mainMapper.getGradeLvlIdByGradeLevel(gradeLevel);
    }

    public List<String> getContactNumbersByStudentId(String sectionId, String studentId){
        return mainMapper.getContactNumbersByStudentId(sectionId, studentId);
    }


    public List<Student> getStudentListBySectionId(String sectionId) {
        return mainMapper.getStudentListBySectionId(sectionId);
    }

    public List<User> getUserList() {
        return mainMapper.getUserList();
    }

    public List<Guidance> getGuidanceList() {
        return mainMapper.getGuidanceList();
    }

    public List<String> getContactNumbers(String gradeLevelId, String sectionId, String studentId) {
        return mainMapper.getContactNumbers(gradeLevelId, sectionId, studentId);
    }


    public List<String> getStudentNumberBySectionId(String id) {
        return mainMapper.getStudentContactNumbersBySectionId(id);
    }

    public Parent getParentNumberByStudentId(String s) {
        return mainMapper.getParentNumberByStudentId(s);
    }

    public boolean doesParentEmailExist(String email) {
        return mainMapper.doesParentEmailExist(email);
    }

    public int validateUser(String username, String email) {
        return mainMapper.validateUser(username, email);
    }

    public String getParentIdsBySectionId(String sectionId) {
        return mainMapper.getParentIdsBySectionId(sectionId);
    }

    public List<MessageJson> getFilteredAnnouncements(AttendanceParams attParams, String userId) {
        if (attParams.getDateFrom().equals(""))
            attParams.setDateFrom("2015-01-01 00:00");
        else {
            String[] arr = attParams.getDateFrom().split("T");
            attParams.setDateFrom(String.join(" ", arr));
        }
        if (attParams.getDateTo().equals(""))
            attParams.setDateTo("2019-01-01 23:59");
        else {
            String[] arr = attParams.getDateTo().split("T");
            attParams.setDateFrom(String.join(" ", arr));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateF = new Date();
        Date dateT = new Date();
        try {
            dateF = sdf.parse(attParams.getDateFrom());
            dateT = sdf.parse(attParams.getDateTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainMapper.getFilteredAnnouncements(dateF, dateT, userId);
    }
}
