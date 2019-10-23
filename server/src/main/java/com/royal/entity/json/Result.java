package com.royal.entity.json;

import java.io.Serializable;

import com.royal.entity.enums.ResultEnum;

public class Result implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 9062701623341190354L;

    private String msgCode;

    private String msg;

    private Object data;

    private Long msgTime;


    public Result(Object data) {
        super();
        this.msgCode = ResultEnum.SUCCEED.getKey();
        this.msg = ResultEnum.SUCCEED.getValue();
        this.data = data;
    }

    public Result(boolean succeed) {
        super();
        if (succeed) {
            this.msgCode = ResultEnum.SUCCEED.getKey();
            this.msg = ResultEnum.SUCCEED.getValue();
        }
    }

    public Result() {
        super();
    }


    public Result(ResultEnum resultEnum, Object data) {
        super();
        this.msgCode = resultEnum.getKey();
        this.msg = resultEnum.getValue();
        this.data = data;
    }

    public Result(ResultEnum resultEnum) {
        super();
        this.msgCode = resultEnum.getKey();
        this.msg = resultEnum.getValue();
    }

    public Result(Exception e) {
        super();
        this.msgCode = ResultEnum.SERVER_ERROR.getKey();
        this.msg = ResultEnum.SERVER_ERROR.getValue();
        this.data = e.getMessage();
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getMsgTime() {
        return msgTime == null ? System.currentTimeMillis() : msgTime;
    }

    public void setMsgTime(Long msgTime) {
        this.msgTime = msgTime;
    }



    @Override
    public String toString() {
        return "Result{" +
                "msgCode='" + msgCode + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", msgTime=" + msgTime +
                '}';
    }

}
