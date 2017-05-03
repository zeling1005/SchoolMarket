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
    //mWebView.loadUrl("file:///android_asset/test.html");
    //context.getClass().getClassLoader().getResourceAsStream("assets/"+资源名);
    public Result<List<Trade>> getTrades(){
        String jsonStr="[\n" +
                "  {\n" +
                "    \"id\": \"0001\",\n" +
                "    \"authorImg\": \"http://opdpjh63a.bkt.clouddn.com/sort_avater_cluo.jpg\",\n" +
                "    \"imgUrls\": \"http://opdpjh63a.bkt.clouddn.com/apple_phone.jpg\",\n" +
                "    \"authorName\": \"tom\",\n" +
                "    \"nowPrice\": 2000,\n" +
                "    \"status\": 0,\n" +
                "    \"title\": \"苹果手机\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"0002\",\n" +
                "    \"authorImg\": \"http://opdpjh63a.bkt.clouddn.com/sort_clothes.jpeg\",\n" +
                "    \"imgUrls\": \"http://opdpjh63a.bkt.clouddn.com/battledore.jpg\",\n" +
                "    \"authorName\": \"peter\",\n" +
                "    \"nowPrice\": 50,\n" +
                "    \"status\": 0,\n" +
                "    \"title\": \"羽毛球拍\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"0003\",\n" +
                "    \"authorImg\": \"http://opdpjh63a.bkt.clouddn.com/sort_body.jpg\",\n" +
                "    \"imgUrls\": \"http://opdpjh63a.bkt.clouddn.com/book.jpg\",\n" +
                "    \"authorName\": \"nice\",\n" +
                "    \"nowPrice\": 20,\n" +
                "    \"status\": 0,\n" +
                "    \"title\": \"传统民谣书系\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"0004\",\n" +
                "    \"authorImg\": \"http://opdpjh63a.bkt.clouddn.com/sort_avater_cluo.jpg\",\n" +
                "    \"imgUrls\": \"http://opdpjh63a.bkt.clouddn.com/mac_computer.jpg\",\n" +
                "    \"authorName\": \"tom\",\n" +
                "    \"nowPrice\": 5000,\n" +
                "    \"status\": 0,\n" +
                "    \"title\": \"苹果电脑\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"0005\",\n" +
                "    \"authorImg\": \"http://opdpjh63a.bkt.clouddn.com/sort_clothes.jpeg\",\n" +
                "    \"imgUrls\": \"http://opdpjh63a.bkt.clouddn.com/table_lamp.jpeg\",\n" +
                "    \"authorName\": \"nice\",\n" +
                "    \"nowPrice\": 30,\n" +
                "    \"status\": 0,\n" +
                "    \"title\": \"台灯\"\n" +
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
