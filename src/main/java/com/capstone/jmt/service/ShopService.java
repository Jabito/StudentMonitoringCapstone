package com.capstone.jmt.service;

import com.capstone.jmt.data.*;
import com.capstone.jmt.mapper.OrderMapper;
import com.capstone.jmt.mapper.ShopMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jabito on 15/02/2017.
 */
@Service("shopService")
public class ShopService {

    private static final Logger logger = LoggerFactory.getLogger(ShopService.class);

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ShopLogin validateUser(ShopLogin user) {
        logger.info("loadUserByUsername");
        ShopLogin account = shopMapper.loadUserByUsername(user.getUsername());
        logger.info("loadUserByUsername", user);
        if (null != account)
            if (passwordEncoder.matches(user.getPassword(), account.getPassword()))
                return account;
        return null;
    }

    public ShopLogin getShopLoginById(String id) {
        logger.info("getShopLoginById");
        ShopLogin shopUser = shopMapper.getShopLoginById(id);
        logger.info("getShopLoginById", shopUser);
        return shopUser;
    }

    public ShopInfo getShopInfoById(String id) {
        logger.info("getShopInfoById");
        ShopInfo shopUser = shopMapper.getShopInfoById(id);
        logger.info("getShopInfoById", shopUser);
        return shopUser;
    }

    public ShopLocation getShopLocationById(String id) {
        logger.info("getShopLocationById");
        ShopLocation customer = shopMapper.getShopLocationById(id);
        logger.info("getShopLocationById", customer);
        return customer;
    }

    public ShopSalesInformation getShopSalesInformationById(String id) {
        logger.info("getShopSalesInformation");
        ShopSalesInformation order = shopMapper.getShopSalesInformationById(id);
        logger.info("getShopSalesInformation", order);
        return order;
    }

    public void updateShopInfo(ShopInfo shop) {
        logger.info("updateShopInfo");
        Integer ret = shopMapper.updateShopInfo(shop);
    }

    public void updateShopLogin(ShopLogin shop) {
        logger.info("updateShopLogin");
        Integer ret = shopMapper.updateShopLogin(shop);
    }

    public void addShopLocation(ShopLocation shop) {
        logger.info("addShopLocation");
        Integer ret = shopMapper.addShopLocation(shop);
    }

    public void updateShopLocation(ShopLocation shop) {
        logger.info("updateShopLocation");
        Integer ret = shopMapper.updateShopLocation(shop);
    }

    public void addShopSalesInformation(ShopSalesInformation shop) {
        logger.info("addContainersOffered");
        Integer ret = shopMapper.addShopSalesInformation(shop);
    }

    public void updateShopSalesInformation(ShopSalesInformation shop) {
        logger.info("updateContainersOffered");
        Integer ret = shopMapper.updateShopSalesInformation(shop);
    }

    public Double getTotalSales(String shopId) {
        logger.info("getTotalSales");
        Double sales = shopMapper.getTotalSalesById(shopId);

        return sales;
    }

    public Integer getSalesCount(String shopId) {
        logger.info("getSalesCount");
        Integer count = shopMapper.getSalesCount(shopId);

        return count;
    }

    public String getShopRating(String shopId) {
        logger.info("getShopRating");
        return shopMapper.getShopRating(shopId);
    }

    public List getOrdersForToday(String shopId) {
        logger.info("getOrdersForToday");
        return orderMapper.getOrdersForToday(shopId);
    }

    public List getLastSevenDays(String shopId) {
        logger.info("getLastSevenDays");
        List<OrderInfo> orders = orderMapper.getLastSevenDays(shopId);
        String startDate;
        Integer sold=0;
        Double sales=0.0;
        List<LastSevenDays> lastSeven = new ArrayList<>();
        if(null != orders) {
            startDate = orders.get(0).getCreatedOn();
            for (OrderInfo order : orders) {
                if (!order.getCreatedOn().equals(startDate)) {
                    LastSevenDays lsd = new LastSevenDays();
                    lsd.setDate(startDate);
                    lsd.setSold(sold);
                    lsd.setSales(sales);
                    lastSeven.add(lsd);
                    sold = 0;
                    sales = 0.0;
                    startDate = order.getCreatedOn();
                    if(null != order.getRoundOrdered())
                        sold += order.getRoundOrdered();
                    if(null != order.getSlimOrdered())
                        sold += order.getSlimOrdered();
                    if(null != order.getTotalCost())
                        sales += order.getTotalCost();
                } else {
                    if(null != order.getRoundOrdered())
                        sold += order.getRoundOrdered();
                    if(null != order.getSlimOrdered())
                        sold += order.getSlimOrdered();
                    if(null != order.getTotalCost())
                        sales += order.getTotalCost();
                }
            }
            LastSevenDays lsd = new LastSevenDays();
            lsd.setDate(startDate);
            lsd.setSold(sold);
            lsd.setSales(sales);
            lastSeven.add(lsd);
        }

        return lastSeven;
    }
}
