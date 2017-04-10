package com.peter.schoolmarket.dao;

import com.peter.schoolmarket.data.pojo.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by PetterChen on 2017/4/10.
 */

public interface UserDao {

    void insert(User user) throws SQLException;

    void deleteUser(String id) throws SQLException;

    void deleteAll() throws SQLException;

    List<User> getAllUser() throws SQLException;

    void closeRealm();
}
