package com.wei.fly.dao;

import com.wei.fly.dao.entity.User;
import com.wei.fly.interfaces.request.user.ListUserRequest;

import java.util.List;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/15
 * @Version 1.0.0
 */
public interface UserMapper {

    int insert(User user);

    int update(User user);

    User selectByUserId(String userId);

    List<User> selectByCondition(User user);

    User selectByOpenId(String openId);

    int countUser(ListUserRequest request);

    List<User> listUser(ListUserRequest request);

    void delData();
}
