package com.peter.schoolmarket.util;

import com.peter.schoolmarket.data.pojo.Msg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PetterChen on 2017/5/17.
 */

public class MsgUtil {

    private static List<Msg> msgsList;

    public static List<Msg> getMsgs() {
        msgsList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Msg tem = new Msg();
            tem.setTitle("aa");
            tem.setContent("aa");
            tem.setCreateTime(System.currentTimeMillis());
            msgsList.add(tem);
        }
        return msgsList;
    }

}
