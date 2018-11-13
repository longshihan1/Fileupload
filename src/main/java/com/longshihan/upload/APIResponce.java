package com.longshihan.upload;

public class APIResponce <T> {
    private int code;
    private T data;
    private String msg;

    public APIResponce(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public APIResponce(T data){
        this.data=data;
        this.msg="成功";
        this.code=0;

    }

    public static APIResponce success(){
        return new APIResponce(1,"成功");
    }
    public static APIResponce fail(String msg){
        return new APIResponce(0, msg);
    }

    @Override
    public String toString() {
        return GsonUtils.toJsonString(this);
    }
}
