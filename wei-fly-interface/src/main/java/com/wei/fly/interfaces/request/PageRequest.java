package com.wei.fly.interfaces.request;

import com.wei.fly.interfaces.annotation.RequiredParam;
import lombok.Data;

/**
 * @author Feinik
 * @Discription 分页request
 * @Data 2019/3/25
 * @Version 1.0.0
 */
@Data
public class PageRequest extends BaseRequest {

    /** 每页大小 */
    @RequiredParam
    protected int pageSize = 20;

    /** 当前页 */
    @RequiredParam
    protected int pageNum = 1;

    /** 记录开始位置 */
    protected int pageFrom;

    /** 查询开始时间 */
    protected String startTime;

    /** 查询结束时间 */
    protected String endTime;

}
