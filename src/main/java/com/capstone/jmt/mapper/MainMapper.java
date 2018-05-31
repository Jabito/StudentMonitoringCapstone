package com.capstone.jmt.mapper;

import com.capstone.jmt.data.*;
import com.capstone.jmt.entity.*;
import org.apache.ibatis.annotations.Param;

import java.sql.Blob;
import java.util.List;

/**
 * Created by Jabito on 08/08/2017.
 */
public interface MainMapper {
    User getUserByUsername(@Param("username") String username);

    Student getStudent(@Param("id") String studentId);

    void updateStudent(@Param("student") Student student);

    void addStudent(@Param("student")Student student);

    void deleteStudentById(@Param("id") String id);

    void postAnnouncement(@Param("mj") MessageJson mj);

    TapLog getLastTapDetails(@Param("rfid") String rfid);

    void addUser(@Param("user") User user);

    TapLog getLastTapDetailsByStudentId(@Param("studentId") String studentId);

    List<TapLog> getTapListDetailsByStudentId(@Param("studentId") String studentId);

    List<RefGradeLevel> getGradeLevelList();

    List<RefSection> getSectionList(@Param("gradeLvlId") int gradeLvlId);

    void addTeacher(@Param("guidance") Guidance guidance);

    Guidance getGuidance(@Param("id") String id);

    Parent getParent(@Param("id") String id);

    EmergencyContact getEmergencyContact(@Param("id") String id);

    User getUserById(@Param("id") String id);

    int getLastId(@Param("id") int id);

    void incrementId(@Param("id") int id);

    void addParent(@Param("parent") Parent parent);

    void addEmergencyContact(@Param("eContact")EmergencyContact eContact);

    List<MessageJson> getAnnouncementsByParentId(@Param("searchString") String searchString);

    List<Parent> getFilteredParentsBySection(@Param("section") String section);

    List<Parent> getParentsByGradeLevelId(@Param("gradeLevelId") Integer gradeLevelId);

    void processRfidTap(@Param("tapLog") TapLog tapLog);

    void toggleSMS(@Param("parentId") String parentId, @Param("mode") boolean mode);

    List<Student> getAllStudents();

    Student getStudentByRfid(@Param("rfid") String rfid);

    Student getStudIn();

    Student getStudOut();

    void addGuidanceRecord(@Param("gr")GuidanceRecord gr);

    List<GuidanceRecord> getGuidanceRecord(@Param("studentId")String studentId);

    List<GuidanceRecord> getGuidanceRecordList();

    void saveImage(@Param("imageHolder") PictureObject imageHolder);

    PictureObject retrieveImage(@Param("userId") String fileId);

    List<Student> getStudentList();

    Student getStudentById(@Param("id") String id);

    List<MessageJson> getAnnouncementsByUserId(@Param("userId") String userId);

    List<Student> getStudentsBySearchString(@Param("searchString") String searchString);

    List<RefUserType> getUserType();

    String getGradelevelStringById(@Param("gradeLvlId") Integer gradeLvlId);

    List<AttendanceRow> getWeeklyAttendance();

    List<UserList> getUsersByUserTypeId(@Param("userTypeId") int userTypeId);

    List<TapLog> getTapAllTopLogs();

    Parent getParentByStudentId(@Param("studentId") String studentId);

    String getLastTapDate(@Param("mode")String mode);

    List<TapLog> getTapLogsByParentId(@Param("parentId") String id);

    List<RefSection> getSectionListByGradeLevel(String gradeLevel);

    int getGradeLvlIdByGradeLevel(@Param("gradeLevel") String gradeLevel);

    List<String> getContactNumbersByStudentId(@Param("sectionId") String sectionId);

    List<Student> getStudentListBySectionId(@Param("sectionId") String sectionId);
}
