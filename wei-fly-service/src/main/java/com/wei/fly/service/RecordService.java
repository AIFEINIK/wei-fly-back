package com.wei.fly.service;

import com.wei.fly.interfaces.request.record.ListRecordRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.record.RecordResponse;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/5/7
 * @Version 1.0.0
 */
public interface RecordService {
    Result<Page<RecordResponse>> listRecord(ListRecordRequest request);
}
