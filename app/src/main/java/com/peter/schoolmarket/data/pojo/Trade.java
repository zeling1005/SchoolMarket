package com.peter.schoolmarket.data.pojo;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by PetterChen on 2017/4/28.
 */

public class Trade extends RealmObject implements Serializable {
    @PrimaryKey
    private int id;//商品ID T001
    private String title;//商品名字
    private int authorId;
    private int payId;
    private double originalPrice;//商品原价格
    private double nowPrice;//二手商品价格
    private String tagName;//商品类别
    private String imgUrl;//商品图片
    private String describe;//描述
    private long createTime;//商品创建时间
    private int status;//商品状态。0:在售，1:售出
    @Ignore
    private boolean releaseCheck;//为了在发布时方便检查数据完整性，不存储在数据库中

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(double nowPrice) {
        this.nowPrice = nowPrice;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isReleaseCheck() {
        return releaseCheck;
    }

    public void setReleaseCheck(boolean releaseCheck) {
        this.releaseCheck = releaseCheck;
    }
}
