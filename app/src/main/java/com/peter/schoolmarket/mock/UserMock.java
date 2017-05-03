package com.peter.schoolmarket.mock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.User;

import java.util.List;

/**
 * Created by PetterChen on 2017/5/2.
 */

public class UserMock {
    public Result<String> register(){
        String json="{\"code\":100,\"msg\":\"操作成功\",\"data\":\"001\"}";
        return new Gson().fromJson(json,  new TypeToken<Result<String>>() {}.getType());
    }

    public Result<User> loginOrRegisterNext(){
        String json="{\"code\":100,\"msg\":\"操作成功\",\"data\":{\"id\":\"001\",\"username\":\"admin\",\"password\":" +
                "\"admin\",\"phone\":\"18202429136\",\"avatarUrl\":\"asset:///mock_ing/sort_clothes.jpeg\"}}";//"null"表示传值为String = "null";
        //Gson g = new GsonBuilder().serializeNulls().create();字段为空
        return new Gson().fromJson(json,  new TypeToken<Result<User>>() {}.getType());
    }

    public Result<User> getUserInfo(){
        String json="{\"code\":100,\"msg\":\"操作成功\",\"data\":{\"id\":\"002\",\"username\":" +
                "\"admin\",\"phone\":\"18202429136\",\"avatarUrl\":\"asset:///mock_ing/sort_clothes.jpeg\"}}";//"null"表示传值为String = "null";
        //Gson g = new GsonBuilder().serializeNulls().create();字段为空
        return new Gson().fromJson(json,  new TypeToken<Result<User>>() {}.getType());
    }
}
