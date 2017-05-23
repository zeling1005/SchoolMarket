package com.peter.schoolmarket.mvp.find;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.data.pojo.User;

import java.util.List;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/4/28.
 */

public interface IGainListener {
    void onReqComplete(Result<List<Trade>> result);//从服务器中得到数据后的操作
    void onSearchReqComplete(Result<List<Trade>> result);//从服务器中得到数据后的操作
}
