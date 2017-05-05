package com.peter.schoolmarket.mvp.more;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Notice;

import java.util.List;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/5/5.
 */

public interface IMoreListener {
    void onReqComplete(Result<List<Notice>> result, Realm realm);//从服务器中得到数据后的操作
}
