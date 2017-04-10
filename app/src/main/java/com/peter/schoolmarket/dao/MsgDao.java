package com.peter.schoolmarket.dao;

import com.peter.schoolmarket.data.pojo.Msg;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by PetterChen on 2017/4/10.
 * 操作数据库接口MsgDao
 */

public interface MsgDao {

    void insert(Msg msg) throws SQLException;

    void deleteMsg(String id) throws SQLException;

    void deleteAll() throws SQLException;

    List<Msg> getAllMsg() throws SQLException;

    void closeRealm();
}
