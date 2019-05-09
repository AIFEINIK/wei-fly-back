package com.wei.fly.interfaces;

import com.wei.fly.interfaces.request.record.ListRecordRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.record.RecordResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
@RequestMapping("/record")
public interface RecordFacade {

    @GetMapping("listRecord")
    Result<Page<RecordResponse>> listRecord(ListRecordRequest request, HttpServletRequest servletRequest);

}
