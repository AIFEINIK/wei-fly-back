package com.wei.fly.domain;

import com.wei.fly.dao.UniqueNumMapper;
import com.wei.fly.interfaces.enums.BizTypeEnum;
import com.wei.fly.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/16
 * @Version 1.0.0
 */
@Component
public class UniqueDomain {

    @Autowired
    private UniqueNumMapper uniqueNumMapper;

    public String getUniqueCode(String codePrefix, BizTypeEnum bizType) {
        final long num = uniqueNumMapper.getUniqueNum(bizType.getIndex());
        final String date = DateUtils.getCurDateYYYYMMDD();
        return codePrefix + date + String.format("%04d", num);
    }
}
