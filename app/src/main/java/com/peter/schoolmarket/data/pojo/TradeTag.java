package com.peter.schoolmarket.data.pojo;

import com.peter.schoolmarket.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by PetterChen on 2017/4/29.
 */

public class TradeTag {
    private int rId;//分类的id
    private String name;//分类的名称

    public TradeTag() {
    }

    public int getRId() {
        return rId;
    }

    public String getName() {
        return name;
    }

    public void setRId(int rId) {
        this.rId = rId;
    }

    public void setName(String name) {
        this.name = name;
    }

}