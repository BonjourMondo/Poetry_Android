package com.example.sf.TimeShaft;

/**
 * Created by sf on 18-3-24.
 * 解决String对象不能直接用于Handle内部类参数的问题
 */

public class StringStore {
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String result;
}
