package com.wei.fly.dao;

import com.wei.fly.dao.entity.User;
import com.wei.fly.dao.entity.UserCard;
import com.wei.fly.interfaces.request.user.ListUserRequest;
import org.apache.ibatis.annotations.Param;

import javax.ws.rs.PathParam;
import java.util.List;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/15
 * @Version 1.0.0
 */
public interface UserCardMapper {

    int insert(UserCard userCard);

    int update(UserCard userCard);

    UserCard selectByUserId(@Param("userId") String userId);

}
