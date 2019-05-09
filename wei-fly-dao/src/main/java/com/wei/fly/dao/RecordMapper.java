package com.wei.fly.dao;

import com.wei.fly.dao.entity.Record;
import com.wei.fly.interfaces.request.record.ListRecordRequest;

import java.util.List;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/5/7
 * @Version 1.0.0
 */
public interface RecordMapper {

    int countRecord(ListRecordRequest request);

    List<Record> listRecord(ListRecordRequest request);

    void insert(Record record);
}
