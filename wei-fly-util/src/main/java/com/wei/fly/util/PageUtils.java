package com.wei.fly.util;

import com.wei.fly.interfaces.request.PageRequest;

/**
 * @author Feinik
 * @Discription 分页计算
 * @Data 2019/3/21
 * @Version 1.0.0
 */
public class PageUtils {

    public static <T> T calculatePage(PageRequest request, int count) {
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();

        if (pageNum < 1) {
            throw new IllegalArgumentException("pageNum: " + pageNum);
        }
        if (pageSize < 1) {
            throw new IllegalArgumentException("pageSize: " + pageSize);
        }
        if (count < 0) {
            throw new IllegalArgumentException("dataTotal: " + count);
        }
        int pageTotal = count / pageSize;
        if (count % pageSize > 0) {
            pageTotal++;
        }
        if (pageNum > pageTotal) {
            pageNum = pageTotal;
        }
        if (pageTotal == 0) {
            pageNum = 1;
        }
        int pageFrom = (pageNum - 1) * pageSize;
        if (pageFrom > count) {
            pageFrom = count;
        }

        request.setPageFrom(pageFrom);
        return (T) request;
    }

    public static int getPageFrom(int pageNum,int pageSize){
        return  (pageNum - 1) * pageSize;
    }
}
