package com.capstone.jmt.controller;

import com.capstone.jmt.data.AddUserJson;
import com.capstone.jmt.data.PictureObject;
import com.capstone.jmt.data.ShopLogin;
import com.capstone.jmt.data.TapLog;
import com.capstone.jmt.entity.Guidance;
import com.capstone.jmt.entity.Parent;
import com.capstone.jmt.entity.Student;
import com.capstone.jmt.entity.User;
import com.capstone.jmt.service.AndroidPushNotificationsService;
import com.capstone.jmt.service.MainService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jabito on 08/08/2017.
 */
@Controller
@RequestMapping(value = "/")
@SessionAttributes("appUser")
public class MainWebController {

    private final String TOPIC = "Capstone";


    @Autowired
    MainService mainService;

    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;

    @ModelAttribute("appUSer")
    public User getShopUser() {
        return new User();
    }

    @ModelAttribute("student")
    public Student getStudent() {
        return new Student();
    }

    //TODO UPLOAD PHOTO CONTROLLER
//    @ResponseBody
//    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId,
//                                         @RequestParam("appUsername") String appUsername) {
//        String name = file.getName();
//        System.out.println(name);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        String id = sdf.format(new Date());
//        String contentType = file.getContentType();
//        PictureObject imageHolder = new PictureObject();
//        imageHolder.setStudentId(userId);
//        imageHolder.setFileId(id);
//        imageHolder.setContentType(contentType);
//        imageHolder.setOriginalFileName(file.getOriginalFilename());
//        imageHolder.setFileNameNoSuffix(file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf(".")));
//        imageHolder.setFileSuffix(file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")));
//
//        HashMap<String, Object> response = new HashMap<>();
//
//        if (!file.isEmpty()) {
//            try {
//                imageHolder.setContent(file.getBytes());
//                mainService.saveImage(imageHolder);
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
//    }
//TODO PUSH NOTIF CONTROLLER
    @RequestMapping(value="/sendPushNotif", method = RequestMethod.GET, produces = "application/json")
    public String sendPushNotif() throws JSONException{

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "You have been hacked.");
        notification.put("body", "Enjoy.");

        JSONObject data = new JSONObject();
        data.put("Key-1", "JSA Data 1");
        data.put("Key-2", "JSA Data 2");

        body.put("notification", notification);
        body.put("data", data);
        System.out.println(notification);
        System.out.println(data);

/**
 {
 "notification": {
 "title": "JSA Notification",
 "body": "Happy Message!"
 },
 "data": {
 "Key-1": "JSA Data 1",
 "Key-2": "JSA Data 2"
 },
 "to": "/topics/JavaSampleApproach",
 "priority": "high"
 }
 */
        HttpEntity<String> request = new HttpEntity<>(body.toString());
        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();

            return "redirect:/login";
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return "redirect:/login";
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
            System.out.println("Null");
        }

        System.out.println("RETURNED USER: " + returnedUser.getUsername());
        model.addAttribute("User", returnedUser);

        return "redirect:/homepage";
    }


    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String showDashboard(@ModelAttribute("appUser") User user, Model model) {
        System.out.println("HOMEPAGE: " + user.getUsername());
        if (null != user.getUsername()) {
            model.addAttribute("User", user);
            return "dashboard";
        }
        return "redirect:/login";
    }


    @RequestMapping(value = "/getStudent", method = RequestMethod.GET)
    public String shopAddStudent(Model model, @RequestBody(required = false) PictureObject pictureObject) {

        model.addAttribute("student", getStudent());
        model.addAttribute("pic", new PictureObject());

        if (null == pictureObject) {
            System.out.println("walang laman");
        } else {
            System.out.println("mays laman");
        }

        return "addStudent";
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public String addStudent(@ModelAttribute("appUSer") User appUser, @Valid Student student, BindingResult bindingResult, Model model) {


        System.out.println("student first name: " + student.getFirstName());
        System.out.println("student last name: " + student.getLastName());
        try {
            student.setCreatedBy(appUser.getUsername());
            mainService.addStudent(student);
            System.out.println("SUCCESS!!");

            return "redirect:/login";
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
    public String addNewParent(@ModelAttribute("appUSer") User appUser, @Valid Parent parent, BindingResult bindingResult, Model model) {

        parent.setCreatedBy(appUser.getUsername());
        parent.setUpdatedBy(appUser.getUsername());
        mainService.addParent(parent);

        return "redirect:/login";
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
        return "redirect:/login";
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
        return "redirect:/login";

    }

    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public String shopMonitor(@RequestParam(value = "rfid", required = false) String rfid, Model model) {
        mainService.tapStudent(rfid);
        Student studIn = mainService.getStudentByRfidIn();
        Student studOut = mainService.getStudentByRfidOut();
//        TapLog tap = new TapLog();
//        if(null != rfid)
//             tap = (TapLog) mainService.getLastTapEntry(student.getId()).get("tapDetails");
        model.addAttribute("student", new Student());
        model.addAttribute("stud", null != studIn ? studIn : new Student());
        model.addAttribute("stud1", null != studOut ? studOut : new Student());
//        model.addAttribute("tapType", tap != null? tap.getLogType(): "");
        return "monitor";
    }

    @RequestMapping(value = "/monitorStudent", method = RequestMethod.POST)
    public String monitorStudent(@ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {
        System.out.println("STUDENT RFID: " + student.getRfid());
//            mainService.processRfidTap(student.getRfid());
//        Student student1 = mainService.getStudentByRfid(student.getRfid());
//        System.out.println("STUDENT RETRIEVED: " + student1.getFirstName());
//        model.addAttribute("student", student1);

        return "redirect:/monitor?rfid=" + student.getRfid();
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String shopInventory(@ModelAttribute("shopUser") ShopLogin shopUser, Model model) {
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
//        if (shopUser.getId() == null)
//            return "redirect:/login";

//        List<OrderInfo> orders = orderService.getOrdersByShopId(shopUser.getStaffOf());
//        model.addAttribute("orders", orders);
//        model.addAttribute("username", shopUser.getUsername());
//        Double sales = 0.0;
//        for(int x=0; x<orders.size(); x++){
//            if(null!=orders.get(x).getTotalCost())
//                    sales += orders.get(x).getTotalCost();
//        }
//
//        model.addAttribute("totalSales", "P " + sales.toString());
//
        return "attendanceLogs";
    }

    @RequestMapping(value = "/savePhoto", method = RequestMethod.POST)
    public String showSavedPhoto(@RequestParam(value = "myFile", required = false) MultipartFile multipartFile) {


        if (null == multipartFile) {
            return "addStudent";
        } else {
            try {
                PictureObject pictureObject = new PictureObject();


                pictureObject.setOriginalFileName(multipartFile.getOriginalFilename());
                System.out.println("NAME : " + multipartFile.getName());
                System.out.println("CONTENT TYPE : " + multipartFile.getContentType());
                System.out.println("SIZE : " + multipartFile.getSize());
                System.out.println("CONTENT BYTES : " + multipartFile.getBytes().toString());
                System.out.println("ORIGINAL NAME : " + multipartFile.getOriginalFilename());

                pictureObject.setContent(multipartFile.getBytes());
                pictureObject.setContentType(multipartFile.getContentType());
                pictureObject.setFileId("sample");
                pictureObject.setOriginalFileName(multipartFile.getOriginalFilename());
                pictureObject.setStudentId("SID10");

                mainService.saveImage(pictureObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            PictureObject pictureObject = new PictureObject();
//            try {
//                pictureObject.setContent(multipartFile.getBytes());
//                pictureObject.setContentType(multipartFile.getContentType());
//                pictureObject.setOriginalFileName(multipartFile.getOriginalFilename());
//            }catch (IOException e){
//                e.printStackTrace();
//            }


            return "redirect:/login";
        }
    }

}
