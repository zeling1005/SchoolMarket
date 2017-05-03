package com.peter.schoolmarket.mock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.data.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PetterChen on 2017/4/29.
 */

public class TradeTagMock {

    /**
     *
     *
     *{
     *  "authorImg": [
     *      "https://jcalaz.github.io/img/sort_clothes.jpeg"
     *  ],
     *  "imgUrls": [
     *      "https://jcalaz.github.io/img/sort_body.jpg"
     *  ],
     *  "authorName": "安琪"
     *  "nowPrice": 9,
     *  "status": 0,
     *  "title": "3瓶脉动饮料"
     *},
     */
    public Result<List<Trade>> getTrades(){
        String jsonStr="[\n" +
                "  {\n" +
                "    \"id\": \"0001\",\n" +
                "    \"authorImg\": \"https://jcalaz.github.io/img/sort_clothes.jpeg\",\n" +
                "    \"imgUrls\": \"https://jcalaz.github.io/img/sort_body.jpg\",\n" +
                "    \"authorName\": \"安琪\",\n" +
                "    \"nowPrice\": 9,\n" +
                "    \"status\": 0,\n" +
                "    \"title\": \"3瓶脉动饮料\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"0002\",\n" +
                "    \"authorImg\": \"https://jcalaz.github.io/img/sort_avater_cluo.jpg\",\n" +
                "    \"imgUrls\": \"https://jcalaz.github.io/img/bg.jpg\",\n" +
                "    \"authorName\": \"peter\",\n" +
                "    \"nowPrice\": 32,\n" +
                "    \"status\": 0,\n" +
                "    \"title\": \"盆栽\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"0003\",\n" +
                "    \"authorImg\": \"https://jcalaz.github.io/img/sort_clothes.jpeg\",\n" +
                "    \"imgUrls\": \"https://jcalaz.github.io/img/sort_computer.jpg\",\n" +
                "    \"authorName\": \"nice\",\n" +
                "    \"nowPrice\": 3659,\n" +
                "    \"status\": 0,\n" +
                "    \"title\": \"三星笔记本\"\n" +
                "  }\n" +
                "]";
        List<Trade> trades= new Gson().fromJson(jsonStr, new TypeToken<List<Trade>>(){}.getType());
        Result<List<Trade>> result=new Result<>();
        result.setCode(100);
        result.setMsg("");
        result.setData(trades);
        return result;
    }

    public Result<Trade> getTradeDetail(){
        Trade trade=new Trade();
        trade.setTitle("Mac 电脑");
        /*User user=new User();
        user.setId("002");
        user.setUsername("admin");
        user.setAvatarUrl("https://jcalaz.github.io/img/sort_rent.jpg");
        trade.setAuthor(user);*/
        trade.setAuthorId("002");
        trade.setAuthorName("admin2");
        trade.setAuthorImg("https://jcalaz.github.io/img/sort_rent.jpg");
        trade.setDescribe("2014年在京东购买，还在保修期，I7CPU，独立显卡，8成新");
        trade.setNowPrice(3000);
        trade.setOriginalPrice(8000);
        trade.setCreateTime(System.currentTimeMillis());
        trade.setImgUrls("https://jcalaz.github.io/img/sort_computer.jpg");
        Result<Trade> result=new Result<>();
        result.setCode(100);
        result.setMsg("");
        result.setData(trade);
        return result;
    }
}
