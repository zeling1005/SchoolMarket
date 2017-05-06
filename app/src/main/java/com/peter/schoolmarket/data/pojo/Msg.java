package com.peter.schoolmarket.data.pojo;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by PetterChen on 2017/4/10.
 * 封装消息的javabean
 * 后期可以考虑加上图片消息
 */

public class Msg extends RealmObject implements Serializable {

    @PrimaryKey
    private String id;//消息主键，一个编号M001
    private String receiveMsgId;//接收者id
    private String sendMsgId;//发送者id
    private String sendName;
    private String sendImg;
    private String sendMsgTime;//信息发送时间
    private String msgWords;//文字信息

    public void setId(String id) {
        this.id = id;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public void setSendImg(String sendImg) {
        this.sendImg = sendImg;
    }

    public void setSendMsgTime(String sendMsgTime) {
        this.sendMsgTime = sendMsgTime;
    }

    public void setReceiveMsgId(String receiveMsgId) {
        this.receiveMsgId = receiveMsgId;
    }

    public void setSendMsgId(String sendMsgId) {
        this.sendMsgId = sendMsgId;
    }

    public void setMsgWords(String msgWords) {

        this.msgWords = msgWords;
    }

    public String getSendMsgTime() {
        return sendMsgTime;
    }

    public String getSendMsgId() {

        return sendMsgId;
    }

    public String getId() {
        return id;
    }

    public String getSendName() {
        return sendName;
    }

    public String getSendImg() {
        return sendImg;
    }

    public String getReceiveMsgId() {
        return receiveMsgId;
    }

    public String getMsgWords() {
        return msgWords;
    }

    public Msg() {
        this.sendMsgTime = "sendMsgTime";
    }

    public Msg(String id, String receiveMsgId, String sendMsgId, String msgWords) {
        this.id = id;
        this.receiveMsgId = receiveMsgId;
        this.sendMsgId = sendMsgId;
        this.sendMsgTime = "sendMsgTime";
        this.msgWords = msgWords;
    }

}
