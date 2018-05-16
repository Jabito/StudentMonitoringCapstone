package com.capstone.jmt.controller;

import com.capstone.jmt.data.*;
import com.capstone.jmt.entity.Guidance;
import com.capstone.jmt.entity.Parent;
import com.capstone.jmt.entity.Student;
import com.capstone.jmt.entity.User;
import com.capstone.jmt.service.AndroidPushNotificationsService;
import com.capstone.jmt.service.MainService;
import com.capstone.jmt.service.StorageService;
import com.google.api.Http;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.http.*;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.IOUtils;

/**
 * Created by Jabito on 08/08/2017.
 */
@Controller
@RequestMapping(value = "/")
@SessionAttributes("appUser")
public class MainWebController {

    private final String TOPIC = "JavaSampleApproach";
    private final String DEVICE = "";

    @Autowired
    MainService mainService;

    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;

    @ModelAttribute("appUser")
    public User getShopUser() {
        return new User();
    }

    @ModelAttribute("appStudent")
    public Student getStudent() {
        return new Student();
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> send() throws JSONException {

        JSONObject body = new JSONObject();
        body.put("to", DEVICE);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "JSA Notification");
        notification.put("body", "Happy Message!");
        notification.put("sound", "default");

        JSONObject data = new JSONObject();
        data.put("title", "Hacker News");
        data.put("message", "You have been hacked.");

        body.put("notification", notification);
        body.put("data", data);
        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();

            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginShopUser(@RequestParam(value = "error", required = false) String error, HttpServletRequest request,
                                Model model) {
        if (null != error) {
            if (error.equals("1"))
                model.addAttribute("param.error", true);
            else if (error.equals("2"))
                model.addAttribute("param.logout", true);
        }
        model.addAttribute("appUser", getShopUser());
        model.addAttribute("appStudent", getStudent());

        return "login";
    }


    @RequestMapping(value = "loginWebUser", method = RequestMethod.POST)
    public String loginWebUser(@ModelAttribute("appUser") User user, Model model) {

        HashMap<String, Object> returnJson = mainService.loginUser(user.getUsername(), user.getPassword());
        User returnedUser = (User) returnJson.get("User");
        if(returnJson.get("responseCode") != HttpStatus.OK)
            return "redirect:/login?error=1";


        System.out.println("RETURNED USER: " + returnedUser.getUsername());
        model.addAttribute("appUser", returnedUser);

        return "redirect:/homepage";
    }

    private User setUserRole(User user, Model model) {
        User userRet = mainService.getUser(user.getUsername());
        if (null != userRet) {
            model.addAttribute("User", userRet);
            model.addAttribute("admin", userRet.getUserTypeId() == 0);
            model.addAttribute("guidance", userRet.getUserTypeId() == 1);
            model.addAttribute("parent", userRet.getUserTypeId() == 2);
        }
        return userRet;
    }


    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String showDashboard(@RequestParam(value = "added", required = false, defaultValue = "") String added, @ModelAttribute("appUser") User user, Model model) {
        if(null == user)
            return "redirect:/login";
        if(user.getUsername().equals("monitorAdmin"))
            return "redirect:/monitor";
        model.addAttribute("added", added);
        user = setUserRole(user, model);
        return null == user ? "redirect:/login" : "dashboard";
    }


    @RequestMapping(value = "/getStudent", method = RequestMethod.GET)
    public String shopAddStudent(@ModelAttribute("appUser") User user, @RequestParam(value = "gradeLvlId", required = false) Integer gradeLvlId, Model model, @RequestBody(required = false) PictureObject pictureObject) {
        user = setUserRole(user, model);
        if (null == user)
            return "redirect:/login";

        System.out.println("GET STUDENT GRADE LEVEL ID: " + gradeLvlId);
        model.addAttribute("student", getStudent());
        model.addAttribute("pic", new PictureObject());
        model.addAttribute("gradeLevel", mainService.getGradeLevelList());
        gradeLvlId = 0;
        model.addAttribute("section", mainService.getSectionList(gradeLvlId));

        return "addStudent";
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public String sendMessage(@ModelAttribute("appUser") User user, Model model) {
        user = setUserRole(user, model);
        if (null == user)
            return "redirect:/login";

        return "sendMessage";
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public String addStudent(@ModelAttribute("appUser") User appUser, @Valid Student student, BindingResult bindingResult, Model model) {

        try {
            student.setCreatedBy(appUser.getUsername());
            mainService.addStudent(student);

            return "redirect:/homepage?added=Student";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "addStudent";
    }

    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
    public String udpateStudent(@ModelAttribute("appUser") User appUser, @Valid Student student, BindingResult bindingResult, Model model) {

        try {
            mainService.updateStudentInfo(student);
            System.out.println("SUCCESS!!");
            return "redirect:/getStudents";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "students";

    }

    @RequestMapping(value = "/getParent", method = RequestMethod.GET)
    public String getParentOfStudent(@Valid Parent parent, @ModelAttribute("appUser") User user, Model model) {
        user = setUserRole(user, model);
        if (null == user)
            return "redirect:/login";

        model.addAttribute("students", mainService.getAllStudents());
        model.addAttribute("parent", new Parent());
        return "addParent";
    }


    @RequestMapping(value = "/addNewParent", method = RequestMethod.POST)
    public String addNewParent(@ModelAttribute("appUser") User appUser, @Valid Parent parent, BindingResult bindingResult, Model model) {

        parent.setCreatedBy(appUser.getUsername());
        parent.setUpdatedBy(appUser.getUsername());
        mainService.addParent(parent);

        return "redirect:/homepage?added=Parent";
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public String getUserData(@ModelAttribute("appUser") User user,@Valid AddUserJson newUser, Model model) {
        user = setUserRole(user, model);
        if (null == user)
            return "redirect:/login";
        //TODO ADD VALIDATION OF NULL VALUES

        model.addAttribute("newUser", new User());
        model.addAttribute("userType", mainService.getUserType());

        return "addUser";
    }

    @RequestMapping(value = "/getListByUserTypeId", method = RequestMethod.GET)
    public ResponseEntity<?> getListByUsertypeId(@RequestParam("userTypeId") int userTypeId){
        return new ResponseEntity<>(mainService.getUsersByUserTypeId(userTypeId), HttpStatus.OK);
    }

    @RequestMapping(value = "/addNewUser", method = RequestMethod.POST)
    public String postNewUser(@Valid AddUserJson newUser, BindingResult bindingResult, Model model) {

        mainService.addUser(newUser);
        return "redirect:/homepage?added=User";
    }

    @RequestMapping(value = "/getGuidance", method = RequestMethod.GET)
    public String getGuidanceData(@ModelAttribute("appUser") User user, @Valid Guidance guidance, Model model) {
        user = setUserRole(user, model);
        if (null == user)
            return "redirect:/login";

        model.addAttribute("newGuidance", new Guidance());
        return "addGuidance";
    }

    @RequestMapping(value = "/addNewGuidance", method = RequestMethod.POST)
    public String postNewGuidance(@Valid Guidance guidance, BindingResult bindingResult, Model model) {

        System.out.println("GUIDANCE FIRST NAME: " + guidance.getFirstName());
        System.out.println("GUIDANCE LAST NAME: " + guidance.getLastName());

        mainService.addGuidance(guidance);
        return "redirect:/homepage?added=Guidance";

    }

    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public String shopMonitor(@ModelAttribute("appUser") User user, Model model) {
        if(!user.getUsername().equalsIgnoreCase("monitorAdmin"))
            return "redirect:/login";
        Student studIn = mainService.getStudentByRfidIn();
        String gradelevel = "";
        String gradelevel1 = "";
        if(null != studIn)
            gradelevel = mainService.getGradelevelStringById(studIn.getGradeLvlId());
        Student studOut = mainService.getStudentByRfidOut();
        if(null != studOut)
            gradelevel1 = mainService.getGradelevelStringById(studOut.getGradeLvlId());

        model.addAttribute("student", new Student());
        model.addAttribute("studGradelvl", gradelevel);
        model.addAttribute("studGradelvl1", gradelevel1);
        model.addAttribute("stud", null != studIn ? studIn : new Student());
        model.addAttribute("stud1", null != studOut ? studOut : new Student());

        return "monitor";
    }

    @RequestMapping(value = "/monitorStudent", method = RequestMethod.POST)
    public String monitorStudent(@Valid Student student, BindingResult bindingResult, Model model) {
        System.out.println("TAP" + student.getRfid());
        if(null != student.getRfid() && student.getRfid() != ""){
            Student stud = mainService.getStudentByRfid(student.getRfid());
            if(null != stud)
                mainService.tapStudent(student.getRfid());
            mainService.sendSMSLogin(student.getId());
        }

        return "redirect:/monitor";
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String shopInventory(@ModelAttribute("appUser") User user, Model model) {
        user = setUserRole(user, model);
        List<MessageJson> messages = (List<MessageJson>) mainService.getAnnouncements(user.getId()).get("announcements");

        model.addAttribute("messages", messages);
        return null == user ? "redirect:/login" : "inventory";
    }

    @RequestMapping(value = "/attendanceLogs", method = RequestMethod.GET)
    public String showSales(@ModelAttribute("appUser") User user, Model model) {
        user = setUserRole(user, model);
        if(null == user)
            return "redirect:/login";
        List<TapLog> tapLogs = mainService.getTapAllTopLogs();
        if(tapLogs.isEmpty()){
            user = setUserRole(user, model);
            return "redirect:/login";
        }else {
            model.addAttribute("logs", tapLogs);
            return "attendanceLogs";
        }
    }


    @RequestMapping(value = "/guidanceReport", method = RequestMethod.GET)
    public String guidanceReport(@ModelAttribute("appUser") User user, Model model) {
        user = setUserRole(user, model);
        return null == user ? "redirect:/login" : "guidanceReport";
    }

    @RequestMapping(value = "/summaryReport", method = RequestMethod.GET)
    public String summaryReport(@ModelAttribute("appUser") User user, Model model) {
        user = setUserRole(user, model);
        return null == user ? "redirect:/login" : "summaryReport";
    }

    @RequestMapping(value="/postGuidanceReport", method = RequestMethod.POST)
    public ResponseEntity<?> postGuidanceReport(@ModelAttribute("appUser") User user,@RequestBody AddReportModel reportModel){
        HashMap<String, Object> response = new HashMap<>();
        GuidanceRecord gr = new GuidanceRecord();
        gr.setCreatedBy(user.getUsername());
        gr.setReason(reportModel.getMessage());
        gr.setStudentId(reportModel.getStudentId());
        gr.setCreatedOn(new Date());
        gr.setDateOfIncident(reportModel.getDateOfIncident());
        gr.setCaseOfIncident(reportModel.getCaseOfIncident());
        gr.setNameOfGuardian(reportModel.getGuardianName());
        Parent parent = mainService.getParentByStudentId(reportModel.getStudentId());

        if(null != parent)
            if(parent.getSmsNotif())
                response.put("contactNo", parent.getOfficeNo());

        try {
            mainService.sendFirebase(reportModel.getMessage());
        }catch(Exception e){}

        mainService.addGuidanceRecord(gr);
        response.put("message", reportModel.getMessage());
        response.put("responseCode", 200);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/getStudentsBySearch", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentsBySearch(@RequestParam(value = "searchText") String searchText) {
        HashMap<String, Object> response = new HashMap<>();
        System.out.println("Search Text: " + searchText);

        List<Student> studentList = mainService.getStudentsBySearch(searchText);
        if (studentList.isEmpty()) {
            response.put("responseCode", 404);
            response.put("responseDesc", HttpStatus.NOT_FOUND);
        } else {
            response.put("responseCode", 200);
            response.put("studList", studentList);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @RequestMapping(value = "/getAttendanceLogsDetails", method = RequestMethod.GET)
    public ResponseEntity<?> getAttendanceLogsDetails(@RequestParam(value = "studId") String studId) {
        HashMap<String, Object> response = new HashMap<>();
        System.out.println("Student Id selected: " + studId);
//        List<TapLog> returnList = ;

//        if(returnList.isEmpty()) {
//            response.put("responseDesc", HttpStatus.NOT_FOUND);
//            response.put("responseCode", 404);
//        }
//        response.put("tapLogList", returnList);
        return new ResponseEntity<>((List<TapLog>) mainService.getTapLogOfStudent(studId).get("tapListDetails"), HttpStatus.OK);

    }

    @RequestMapping(value = "/getStudents", method = RequestMethod.GET)
    public String getStudentList(@ModelAttribute("appUser") User user, Model model) {
        model.addAttribute("student", getStudent());
        user = setUserRole(user, model);
        if (null == user)
            return "redirect:/login";

        List<Student> studentList = mainService.getStudentList();
        if (null == studentList) {
            return "redirect:/login";
        } else {
            model.addAttribute("studList", studentList);
            return "students";
        }
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String showBottleSales(@ModelAttribute("appUser") User user, Model model) {
        user = setUserRole(user, model);
        return null == user ? "redirect:/login" : "bottlesales";
    }

    @RequestMapping(value = "/selectGradeLevel", method = RequestMethod.GET)
    public ResponseEntity<?> selectGradeLevel(@RequestParam(value = "gradeLvlId") int gradeLvlId) {
        HashMap<String, Object> response = new HashMap<>();
        System.out.println("GRADE LEVEL SELECTED: " + gradeLvlId);
        List<RefSection> returnList = mainService.getSectionList(gradeLvlId);

        response.put("section", returnList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/selectUserTypeList", method = RequestMethod.GET)
    public ResponseEntity<?> selectUserTypeList(@RequestParam(value = "userTypeId") int userTypeId) {
        HashMap<String, Object> response = new HashMap<>();
        System.out.println("SELECTED USER TYPE: " + userTypeId);
        List<RefSection> returnList = mainService.getSectionList(userTypeId);

        response.put("section", returnList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/getStudentByStudId", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentByStudId(@RequestParam(value = "id") String id) {
        HashMap<String, Object> response = new HashMap<>();
        System.out.println("SELECTED USER TYPE " + id);

        Student student = mainService.getStudentById(id);
        if(null== student){
            response.put("responseCode", 404);
        }else{
            response.put("stud", student);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/showStudentInfo", method = RequestMethod.GET)
    public String showStudentInfo(@ModelAttribute("appStudent") Student student, Model model, @RequestParam(value = "id") String id) {

        if(null != id)
            student = mainService.getStudentById(id);
        System.out.println("SID " + student.getId());
        model.addAttribute("gradeLevel", mainService.getGradeLevelList());
        model.addAttribute("section", mainService.getSectionList(0));
        if (null == student) {
            return "studentInfo";
        } else {
            model.addAttribute("student", student);
            model.addAttribute("appStudent", student);
            return "studentInfo";
        }
    }


    /**
     * For File Storage
     */

    @Autowired
    StorageService storageService;


    @PostMapping("/upImage")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam(value = "userId", required = false) String userId,
                                   Model model) {
        try {
            storageService.store(file);
            model.addAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
            PictureObject po = new PictureObject();
            po.setStudentId(null == userId ? "SID15" : userId);
            po.setOriginalFileName(file.getOriginalFilename());
            mainService.saveImage(po);
        } catch (Exception e) {
            model.addAttribute("message", "FAIL to upload " + file.getOriginalFilename() + "!");
        }

        return "redirect:/homepage?added=StudImage";
    }

    @RequestMapping("/getPictureFilename")
    public ResponseEntity<?> getPictureObject(@RequestParam("userId") String userId) {
        return new ResponseEntity<>(mainService.retrieveImage(userId).getOriginalFileName(), HttpStatus.OK);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @RequestMapping(value = "/loadImage", method = RequestMethod.GET)
    public ResponseEntity<byte[]> loadImage(@RequestParam("studId") String studId) {
        System.out.println("LOAD" + studId);
        HashMap<String, Object> response = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        try {
        Resource file = storageService.loadFile(mainService.retrieveImage(studId).getOriginalFileName());
        if(null == file)
            file = storageService.loadFile("image.png");

            InputStream in = file.getInputStream();
            byte[] media = IOUtils.toByteArray(in);
//            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<byte[]>(Base64.getEncoder().encode(media), headers, HttpStatus.OK);
        } catch (IOException e) {
            System.out.println("ERROR LOADING IMAGE");
            response.put("responseDesc", "Failed to retrieve image.");
            return new ResponseEntity<byte[]>(new byte[1], HttpStatus.OK);
        }
    }

    /** END FILE STORAGE */

    @RequestMapping(value = "/getWeeklyAttendance", method = RequestMethod.GET)
    public ResponseEntity<?> getWeeklyAttendance(){
        return new ResponseEntity<>(mainService.getWeeklyAttendance(), HttpStatus.OK);
    }
}
