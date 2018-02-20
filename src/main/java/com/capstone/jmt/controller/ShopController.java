package com.capstone.jmt.controller;

import com.capstone.jmt.data.*;
import com.capstone.jmt.entity.User;
import com.capstone.jmt.service.AndroidPushNotificationsService;
import com.capstone.jmt.service.ShopService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jabito on 24/02/2017.
 */
@Controller
@RequestMapping(value = "/")

public class ShopController {
//
//    private final String TOPIC = "messages";
//
//    @Autowired
//    private ShopService shopService;
//
//    @Autowired
//    private AndroidPushNotificationsService androidPushNotificationsService;
    //private OrderService orderService;

    /*
    List of all GET Requests
     */
//    @ModelAttribute("shopUser")
//    public User getShopUser() {
//        return new User();
//    }

//    @RequestMapping(value = "sendPushNotif", method = RequestMethod.GET, produces = "application/json")
//    public String sendPushNotif() throws JSONException {
//
//        JSONObject body = new JSONObject();
////        body.put("to", "/topics/" + TOPIC);
////        body.put("priority", "high");
//
//        JSONObject notification = new JSONObject();
//        notification.put("title", "MIKAELA VIRUS");
//        notification.put("body", "Enjoy.");
//
//        JSONObject data = new JSONObject();
//        data.put("Key-1", "JSA Data 1");
//        data.put("Key-2", "JSA Data 2");
//
//        body.put("notification", notification);
//        body.put("data", data);
//        body.put("topic", TOPIC);
//
//        HttpEntity<String> request = new HttpEntity<>(body.toString());
//        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
//        CompletableFuture.allOf(pushNotification).join();
//        try {
//            String firebaseResponse = pushNotification.get();
//
//            return "redirect:/login";
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch(HttpClientErrorException e){
//            e.printStackTrace();
//        }
//
//        return "redirect:/login";
//    }


//    @RequestMapping(value = "/rating", method = RequestMethod.GET)
//    public String shopRating(@ModelAttribute("shopUser") ShopLogin shopUser, Model model) {
//        if (shopUser.getId() == null)
//            return "redirect:/login";
//
//        model.addAttribute("username", shopUser.getUsername());
//
//        return "rating";
//    }

//    @RequestMapping(value = "/main", method = RequestMethod.GET)
//    public String loginShopUser3(Model model) {
//
//
//        return "main";
//    }



//    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
//    public String showTransactions(@ModelAttribute("shopUser") ShopLogin shopUser, Model model) {
////        if (shopUser.getId() == null)
////            return "redirect:/login";
//
//        return "transactions";
//    }


//
//
//    @RequestMapping(value = "/profile", method = RequestMethod.GET)
//    public String shopProfile(@ModelAttribute("shopUser") ShopLogin shopUser, Model model) {
//        if (shopUser.getId() == null)
//            return "redirect:/login";
//
//        model.addAttribute("prices", new ShopSalesInformation());
//        model.addAttribute("shop", shopService.getShopInfoById(shopUser.getStaffOf()));
//        model.addAttribute("water", shopService.getShopSalesInformationById(shopUser.getStaffOf()));
//        model.addAttribute("username", shopUser.getUsername());
//
//        return "profile";
//    }

//    @RequestMapping(value = "/addStudent", method = RequestMethod.GET)
//    public String shopAddStudent(@ModelAttribute("shopUser") ShopLogin shopUser, Model model) {
////        if (shopUser.getId() == null)
////            return "redirect:/login";
////
////        model.addAttribute("prices", new ShopSalesInformation());
////        model.addAttribute("shop", shopService.getShopInfoById(shopUser.getStaffOf()));
////        model.addAttribute("water", shopService.getShopSalesInformationById(shopUser.getStaffOf()));
////        model.addAttribute("username", shopUser.getUsername());
//
//        return "addStudent";
//    }

    /*
    List of all POST Requests
     */
//    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
//    public String loginUser(ShopLogin shop, Model model) {
////        ShopLogin user = shopService.validateUser(shop);
////        if (null != user) {
////            model.addAttribute("shopUser", user);
//            return "redirect:/homepage/";
////        } else {
////            return "redirect:/login/?error=" + "1";
////        }
//    }
//
//    @RequestMapping(value = "/logout", method = RequestMethod.POST)
//    public String logOutUser(@ModelAttribute("shopUser") ShopLogin shopUser, HttpServletRequest request, SessionStatus session) {
//        session.setComplete();
//
//        return "login";
//    }
//
//    @RequestMapping(value = "/updateInventory1", method = RequestMethod.POST)
//    public String updateInventory1(@ModelAttribute("shopUser") ShopLogin shopUser, ShopSalesInformation shop, Model model) {
//
//        shopService.updateRoundStock(shopUser.getId(), shopUser.getStaffOf(), shop.getRoundStock());
//        return "redirect:/inventory";
//    }
//
//    @RequestMapping(value = "/updateInventory2", method = RequestMethod.POST)
//    public String updateInventory2(@ModelAttribute("shopUser") ShopLogin shopUser, ShopSalesInformation shop, Model model) {
//
//        shopService.updateSlimStock(shopUser.getId(), shopUser.getStaffOf(), shop.getSlimStock());
//        return "redirect:/inventory";
//    }
//
//    @RequestMapping(value = "/updatePrices", method = RequestMethod.POST)
//    public String updatePrices(@ModelAttribute("shopUser") ShopLogin shopUser, ShopSalesInformation water, Model model) {
//
//        shopService.updatePrices(shopUser.getUsername(), shopUser.getStaffOf(), water);
//        return "redirect:/inventory";
//    }
//
//    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
//    public String updateProfile(@ModelAttribute("shopUser") ShopLogin shopUser, ShopInfo shop, Model model) {
//
//        shopService.updateProfile(shop, shopUser.getId());
//        return "redirect:/profile";
//    }
}
