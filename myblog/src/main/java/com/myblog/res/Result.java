package com.myblog.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private String msg;
    private int status;
    private Object data;
    public static Result success(String msg, Object data){
        Result res = new Result();
        res.setMsg(msg==null?"操作成功":msg);
        res.setData(data);
        res.setStatus(200);
        return res;
    }


    public static Result fail(String msg, Object data){
        Result res = new Result();
        res.setMsg(msg==null?"操作失败":msg);
        res.setStatus(404);
        res.setData(data);
        return res;
    }


}
