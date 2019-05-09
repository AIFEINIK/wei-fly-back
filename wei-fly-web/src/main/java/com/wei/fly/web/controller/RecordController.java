package com.wei.fly.web.controller;

import com.wei.fly.interfaces.RecordFacade;
import com.wei.fly.interfaces.request.record.ListRecordRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.record.RecordResponse;
import com.wei.fly.interfaces.response.user.UserSessionResponse;
import com.wei.fly.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/5/7
 * @Version 1.0.0
 */
@RestController
@Slf4j
@Api(value = "RecordController", description = "消费记录")
public class RecordController implements RecordFacade {

    @Autowired
    private RecordService recordService;

    @Override
    @ApiOperation(value = "列表展示")
    public Result<Page<RecordResponse>> listRecord(ListRecordRequest request, HttpServletRequest servletRequest) {
        UserSessionResponse sessionUser = (UserSessionResponse) servletRequest.getSession().getAttribute("user");
        request.setUserId(sessionUser.getUserId());
        return recordService.listRecord(request);
    }
}
