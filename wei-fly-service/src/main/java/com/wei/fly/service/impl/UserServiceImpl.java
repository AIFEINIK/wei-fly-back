package com.wei.fly.service.impl;

import com.wei.fly.dao.UserMapper;
import com.wei.fly.dao.entity.User;
import com.wei.fly.interfaces.enums.ReturnStatusEnum;
import com.wei.fly.interfaces.enums.RoleTypeEnum;
import com.wei.fly.interfaces.enums.UserSexEnum;
import com.wei.fly.interfaces.enums.UserStatusEnum;
import com.wei.fly.interfaces.request.user.ListUserRequest;
import com.wei.fly.interfaces.request.user.UserLoginRequest;
import com.wei.fly.interfaces.request.user.UserRegistryRequest;
import com.wei.fly.interfaces.request.user.UserRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.user.UserResponse;
import com.wei.fly.interfaces.response.user.UserSessionResponse;
import com.wei.fly.model.constant.CommonConstant;
import com.wei.fly.service.UserService;
import com.wei.fly.util.BeanUtils;
import com.wei.fly.util.IDGenerator;
import com.wei.fly.util.Md5Util;
import com.wei.fly.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/16
 * @Version 1.0.0
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result backLogin(UserLoginRequest request) {
        final User condition = BeanUtils.transform(User.class, request);
        final List<User> users = userMapper.selectByCondition(condition);
        if (users != null && users.size() > 0) {
            final User user = users.get(0);
            final String secret = Md5Util.encodeMd5(request.getPasswd() + CommonConstant.PASSWD_SOLT, "utf-8");
            if (secret.equals(user.getPasswd())) {
                log.info("user backLogin success");
                final UserSessionResponse res = BeanUtils.transform(UserSessionResponse.class, user, true);
                res.setUserStatus(UserStatusEnum.getType(user.getUserStatus()));
                res.setRoleType(RoleTypeEnum.getType(user.getRoleType()));
                res.setSex(UserSexEnum.getType(user.getSex()));
                return new Result(res);

            } else {
                return new Result(ReturnStatusEnum.USER_PASSWD_INVALID.getValue(),
                        ReturnStatusEnum.USER_PASSWD_INVALID.getName());
            }

        } else {
            return new Result(ReturnStatusEnum.USER_NOT_EXISTS.getValue(),
                    ReturnStatusEnum.USER_NOT_EXISTS.getName());
        }
    }

    @Override
    public Result<Page<UserResponse>> listUser(ListUserRequest request, UserSessionResponse sessionUser) {
        if (RoleTypeEnum.ADMIN != sessionUser.getRoleType()) {
            return new Result(ReturnStatusEnum.PERMISSION_INVALID.getValue(),
                    ReturnStatusEnum.PERMISSION_INVALID.getName());
        }

        int count = userMapper.countUser(request);
        if (count == 0) {
            return Result.emptyPageResult();
        }
        final List<User> users = userMapper.listUser(PageUtils.calculatePage(request, count));
        List<UserResponse> orderResponses = new ArrayList<>();
        for (User user : users) {
            final UserResponse res = BeanUtils.transform(UserResponse.class, user, true);
            res.setUserStatus(UserStatusEnum.getType(user.getUserStatus()));
            res.setRoleType(RoleTypeEnum.getType(user.getRoleType()));
            res.setSex(UserSexEnum.getType(user.getSex()));
            orderResponses.add(res);
        }

        return new Result(new Page(count, orderResponses));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result registryUser(UserRegistryRequest request) {
        try {
            User user = userMapper.selectByOpenId(request.getOpenId());
            if (null == user) {
                //未注册过，开始注册
                user = BeanUtils.transform(User.class, request);
                user.setUserId(String.valueOf(IDGenerator.createId()));
                user.setPhone(StringUtils.EMPTY);
                user.setCreateTime(new Date());
                user.setUpdateTime(new Date());
                user.setPasswd(StringUtils.EMPTY);
                user.setSex(UserSexEnum.MAN.getIndex());
                user.setUserStatus(UserStatusEnum.NORMAL.getIndex());
                user.setUserName(StringUtils.EMPTY);
                user.setRoleType(RoleTypeEnum.CUSTOMER.getIndex());
                userMapper.insert(user);

            } else {
                User update = new User();
                update.setSessionKey(request.getSessionKey());
                update.setUpdateTime(new Date());
                update.setUserId(user.getUserId());
                userMapper.update(update);
            }
            final UserSessionResponse us = BeanUtils.transform(UserSessionResponse.class, user, true);
            us.setSex(UserSexEnum.getType(user.getSex()));
            us.setRoleType(RoleTypeEnum.getType(user.getRoleType()));
            us.setUserStatus(UserStatusEnum.getType(user.getUserStatus()));
            return new Result(us);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(ReturnStatusEnum.UNKNOWN.getValue(),
                ReturnStatusEnum.UNKNOWN.getName());
    }

    @Override
    public Result editUser(UserRequest request) {
        User user = new User();
        user.setUserName(request.getNickName());
        user.setSex(request.getGender() == 0 ? 1 : request.getGender());
        user.setUserId(request.getUserId());
        userMapper.update(user);
        return new Result();
    }

    @Override
    public Result delData(String userId) {
        userMapper.delData();
        return new Result();
    }
}
