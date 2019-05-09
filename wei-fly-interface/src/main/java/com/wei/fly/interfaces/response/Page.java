package com.wei.fly.interfaces.response;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {

    private int total;

    private List<T> datas;

    public Page(int total, List<T> datas) {
        this.total = total;
        this.datas = datas;
    }
}
