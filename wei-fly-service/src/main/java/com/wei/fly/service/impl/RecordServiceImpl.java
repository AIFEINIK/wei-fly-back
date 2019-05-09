package com.wei.fly.service.impl;

import com.wei.fly.dao.RecordMapper;
import com.wei.fly.dao.entity.Record;
import com.wei.fly.interfaces.enums.SeatTypeEnum;
import com.wei.fly.interfaces.request.record.ListRecordRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.record.RecordResponse;
import com.wei.fly.service.RecordService;
import com.wei.fly.util.BeanUtils;
import com.wei.fly.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/5/7
 * @Version 1.0.0
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public Result<Page<RecordResponse>> listRecord(ListRecordRequest request) {
        int count = recordMapper.countRecord(request);
        if (count == 0) {
            return Result.emptyPageResult();
        }
        final List<Record> records = recordMapper.listRecord(PageUtils.calculatePage(request, count));
        List<RecordResponse> recordResponses = new ArrayList<>();
        for (Record record : records) {
            RecordResponse res = BeanUtils.transform(RecordResponse.class, record, true);
            res.setSeatType(SeatTypeEnum.getType(record.getSeatType()));
            recordResponses.add(res);
        }
        return new Result(new Page(count, recordResponses));
    }
}
