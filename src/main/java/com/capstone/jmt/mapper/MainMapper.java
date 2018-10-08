package com.capstone.jmt.mapper;

import com.capstone.jmt.data.*;
import com.capstone.jmt.entity.*;
import org.apache.ibatis.annotations.Param;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

/**
 * Created by Jabito on 08/08/2017.
 */
public interface MainMapper {
    User getUserByUsername(@Param("username") String username);

    Student getStudent(@Param("id") String studentId);

    void updateStudent(@Param("student") Student student);

    void updateParent(@Param("parent") Parent parent);

    void updateUser(@Param("user") User user);

    void updateGuidance(@Param("guidance") Guidance guidance);

    void addStudent(@Param("student")Student student);

    void deleteStudentById(@Param("id") String id);

    void archiveAllStudents(@Param("date") String date);

    void unArchiveAllStudents(@Param("date") String date);

    void unDeleteStudent(@Param("id") String id);

    void deleteParentById(@Param("id") String id);

    void deleteUserById(@Param("id") String id);

    void deleteGuianceById(@Param("id") String id);

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

    List<Student> getAllArchivedStudents();

    Student getStudentByRfid(@Param("rfid") String rfid);

    Student getStudIn();

    Student getStudOut();

    void addGuidanceRecord(@Param("gr")GuidanceRecord gr);

    List<GuidanceRecord> getGuidanceRecord(@Param("studentId")String studentId);

    List<GuidanceRecord> getGuidanceRecordList();

    List<GuidanceRecord> getGuidanceRecordListWithParams(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo,
                                                         @Param("searchString") String searchString, @Param("sid") String sid);

    void saveImage(@Param("imageHolder") PictureObject imageHolder);

    PictureObject retrieveImage(@Param("userId") String fileId);

    List<Student> getStudentList(@Param("date") int date);

    List<Student> getArchivedStudentList(@Param("date") int date);

    List<Parent> getParentList();

    Student getStudentById(@Param("id") String id);

    Student findStudentByFnameLname(@Param("fname") String fname, @Param("lname") String lname);

    List<MessageJson> getAnnouncementsByUserId(@Param("userId") String userId);

    List<Student> getStudentsBySearchString(@Param("searchString") String searchString);

    List<RefUserType> getUserType();

    String getGradelevelStringById(@Param("gradeLvlId") Integer gradeLvlId);

    List<AttendanceRow> getWeeklyAttendance();

    List<UserList> getUsersByUserTypeId(@Param("userTypeId") int userTypeId);

    List<TapLog> getTapAllTopLogs();

    List<TapLog> getFilteredTapLogs(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo,
                                    @Param("searchString") String searchString);

    Parent getParentByStudentId(@Param("studentId") String studentId);

    String getLastTapDate(@Param("mode")String mode);

    List<TapLog> getTapLogsByParentId(@Param("parentId") String id);

    List<TapLog> getFilteredTapLogsByParentId(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo,
                                              @Param("searchString") String searchString, @Param("id") String id);

    List<RefSection> getSectionListByGradeLevel(String gradeLevel);

    int getGradeLvlIdByGradeLevel(@Param("gradeLevel") String gradeLevel);

    List<String> getContactNumbersByStudentId(@Param("sectionId") String sectionId, @Param("studentId") String studentId);

    List<Student> getStudentListBySectionId(@Param("sectionId") String sectionId);

    List<User> getUserList();

    List<Guidance> getGuidanceList();

    List<String> getContactNumbers(@Param("gradeLevelId") String gradeLevelId, @Param("sectionId") String sectionId, @Param("studentId") String studentId);

    List<String> getStudentContactNumbersBySectionId(@Param("id") String id);

    Parent getParentNumberByStudentId(@Param("s") String s);

    boolean doesParentEmailExist(@Param("email") String email);

    int validateUser(@Param("username") String username, @Param("email") String email);

    String getParentIdsBySectionId(@Param("sectionId") String sectionId);

    void updatePassword(@Param("email") String email,@Param("tempPassword") String tempPassword);

    List<MessageJson> getFilteredAnnouncements(@Param("dateFrom") Date dateForm, @Param("dateTo") Date dateTo, @Param("userId") String userId);
}
