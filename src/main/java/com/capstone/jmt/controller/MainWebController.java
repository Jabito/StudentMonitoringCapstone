package com.capstone.jmt.controller;

import com.capstone.jmt.data.AddUserJson;
import com.capstone.jmt.data.PictureObject;
import com.capstone.jmt.data.RefSection;
import com.capstone.jmt.data.TapLog;
import com.capstone.jmt.entity.Guidance;
import com.capstone.jmt.entity.Parent;
import com.capstone.jmt.entity.Student;
import com.capstone.jmt.entity.User;
import com.capstone.jmt.service.AndroidPushNotificationsService;
import com.capstone.jmt.service.MainService;
import com.capstone.jmt.service.StorageService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.ws.Response;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by Jabito on 08/08/2017.
 */
@Controller
@RequestMapping(value = "/")
@SessionAttributes("appUser")
public class MainWebController {

    /**
     * For File Storage
     * */

    @Autowired
    StorageService storageService;


    @PostMapping("/upImage")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam(value = "userId", required = false) String userId,
                                   Model model) {
        try {
            storageService.store(file);
            model.addAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
            PictureObject po = new PictureObject();
            po.setStudentId(null == userId? "SID15":userId);
            po.setOriginalFileName(file.getOriginalFilename());
            mainService.saveImage(po);
        } catch (Exception e) {
            model.addAttribute("message", "FAIL to upload " + file.getOriginalFilename() + "!");
        }

        return "addStudent";
    }

    @RequestMapping("/getPictureFilename")
    public ResponseEntity<?> getPictureObject(@RequestParam("userId") String userId){
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

    /** END FILE STORAGE */

    private final String TOPIC = "StudentMonitoring";
    private final String DEVICE = "";

    @Autowired
    MainService mainService;

    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;

    @ModelAttribute("appUser")
    public User getShopUser() {
        return new User();
    }

    @ModelAttribute("student")
    public Student getStudent() {
        return new Student();
    }


    @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> send() throws JSONException {

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "JSA Notification");
        notification.put("body", "Happy Message!");

        JSONObject data = new JSONObject();
        data.put("Key-1", "JSA Data 1");
        data.put("Key-2", "JSA Data 2");

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

        return "login";
    }


    @RequestMapping(value = "loginWebUser", method = RequestMethod.POST)
    public String loginWebUser(@ModelAttribute("appUser") User user, Model model) {


        System.out.println("USERNAME: " + user.getUsername());
        System.out.println("PASSWORD: " + user.getPassword());

        HashMap<String, Object> returnJson = mainService.loginUser(user.getUsername(), user.getPassword());
        User returnedUser = (User) returnJson.get("User");
        if (null == returnedUser) {
            return "redirect:/login?error=1";
        }

        System.out.println("RETURNED USER: " + returnedUser.getUsername());
        model.addAttribute("User", returnedUser);

        return "redirect:/homepage";
    }


    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String showDashboard(@RequestParam(value = "added", required = false, defaultValue = "") String added, @ModelAttribute("appUser") User user, Model model) {
        model.addAttribute("added", added);

        System.out.println("HOMEPAGE: " + user.getUsername());
        if (null != user.getUsername()) {
            model.addAttribute("User", user);
            return "dashboard";
        }
        return "redirect:/login";
    }


    @RequestMapping(value = "/getStudent", method = RequestMethod.GET)
    public String shopAddStudent(@RequestParam(value = "gradeLvlId", required = false) Integer gradeLvlId, Model model, @RequestBody(required = false) PictureObject pictureObject) {

        System.out.println("GET STUDENT GRADE LEVEL ID: " + gradeLvlId);
        model.addAttribute("student", getStudent());
        model.addAttribute("pic", new PictureObject());
        model.addAttribute("gradeLevel", mainService.getGradeLevelList());
        gradeLvlId = 0;
        model.addAttribute("section", mainService.getSectionList(gradeLvlId));

        if (null == pictureObject) {
            System.out.println("walang laman");
        } else {
            System.out.println("mays laman");
        }

        return "addStudent";
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public String addStudent(@ModelAttribute("appUser") User appUser, @Valid Student student, BindingResult bindingResult, Model model) {


        System.out.println("student first name: " + student.getFirstName());
        System.out.println("student last name: " + student.getLastName());
        try {
            student.setCreatedBy(appUser.getUsername());
            mainService.addStudent(student);
            System.out.println("SUCCESS!!");

            return "redirect:/homepage?added=Student";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "addStudent";
    }

    @RequestMapping(value = "/getParent", method = RequestMethod.GET)
    public String getParentOfStudent(@Valid Parent parent, Model model) {

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
    public String getUserData(@Valid AddUserJson newUser, Model model) {

        //TODO ADD VALIDATION OF NULL VALUES

        model.addAttribute("newUser", new User());
        return "addUser";
    }

    @RequestMapping(value = "/addNewUser", method = RequestMethod.POST)
    public String postNewUser(@Valid AddUserJson newUser, BindingResult bindingResult, Model model) {

        System.out.println("USER username: " + newUser.getUsername());
        System.out.println("USER password: " + newUser.getPassword());

        mainService.addUser(newUser);
        return "redirect:/homepage?added=User";
    }

    @RequestMapping(value = "/getGuidance", method = RequestMethod.GET)
    public String getGuidanceData(@Valid Guidance guidance, Model model) {

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
    public String shopMonitor(@RequestParam(value = "rfid", required = false) String rfid, Model model) {
        mainService.tapStudent(rfid);
        Student studIn = mainService.getStudentByRfidIn();
        Student studOut = mainService.getStudentByRfidOut();

        model.addAttribute("student", new Student());
        model.addAttribute("stud", null != studIn ? studIn : new Student());
        model.addAttribute("stud1", null != studOut ? studOut : new Student());

        return "monitor";
    }

    @RequestMapping(value = "/monitorStudent", method = RequestMethod.POST)
    public String monitorStudent(@ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {
        System.out.println("STUDENT RFID: " + student.getRfid());
//        mainService.processRfidTap(student.getRfid());
//        Student student1 = mainService.getStudentByRfid(student.getRfid());
//        System.out.println("STUDENT RETRIEVED: " + student1.getFirstName());
//        model.addAttribute("student", student1);

        return "redirect:/monitor?rfid=" + student.getRfid();
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String shopInventory(@ModelAttribute("shopUser") User user, Model model) {
//        if (shopUser.getId() == null)
//            return "redirect:/login";
//
//
//        model.addAttribute("shop1", new ShopSalesInformation());
//        model.addAttribute("shop2", new ShopSalesInformation());
//        model.addAttribute("water", new ShopSalesInformation());
//        model.addAttribute("username", shopUser.getUsername());
//        model.addAttribute("inventory", shopService.getShopSalesInformationById(shopUser.getStaffOf()));

        return "inventory";
    }

    @RequestMapping(value = "/attendanceLogs", method = RequestMethod.GET)
    public String showSales(Model model) {


        return "attendanceLogs";
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

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String showBottleSales(@ModelAttribute("user") User user, Model model) {
//        if (shopUser.getId() == null)
//            return "redirect:/login";
//
//        model.addAttribute("username", shopUser.getUsername());
//        model.addAttribute("bottleSalesRecord", orderService.getBottleSales());

        return "bottlesales";
    }

    @RequestMapping(value = "/selectGradeLevel", method = RequestMethod.GET)
    public ResponseEntity<?> selectGradeLevel(@RequestParam(value = "gradeLvlId") int gradeLvlId) {
        HashMap<String, Object> response = new HashMap<>();
        System.out.println("GRADE LEVEL SELECTED: " + gradeLvlId);
        List<RefSection> returnList = mainService.getSectionList(gradeLvlId);

        response.put("section", returnList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
