package com.wei.fly.interfaces.request.record;

import com.wei.fly.interfaces.request.PageRequest;
import lombok.Data;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/5/7
 * @Version 1.0.0
 */
@Data
public class ListRecordRequest extends PageRequest {

    private String userId;
}
