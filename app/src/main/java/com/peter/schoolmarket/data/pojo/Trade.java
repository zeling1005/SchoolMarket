package com.peter.schoolmarket.data.pojo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by PetterChen on 2017/4/28.
 */

public class Trade extends RealmObject implements Serializable {
    @PrimaryKey
    private String id;//商品ID
    private String title;//商品名字
    private User author;//商品所属者
    private long originalPrice;//商品原价格
    private long nowPrice;//二手商品价格
    private String tagName;//商品类别
    private String imgUrls;//商品图片
    private String describe;//描述
    private long createTime;//商品创建时间
    private int status;//商品状态。0:在售，1:售出
    @Expose
    private boolean releaseCheck;//为了在发布时方便检查数据完整性，不存储在数据库中

    @Override
    public String toString() {
        return "Trade{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", originalPrice=" + originalPrice +
                ", tagName='" + tagName + '\'' +
                ", imgUrls='" + imgUrls + '\'' +
                ", status=" + status +
                ", releaseCheck=" + releaseCheck +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setOriginalPrice(long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setNowPrice(long nowPrice) {
        this.nowPrice = nowPrice;
    }

    public void setTagName(String type) {
        this.tagName = type;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setReleaseCheck(boolean releaseCheck) {
        this.releaseCheck = releaseCheck;
    }

    public String getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getTitle() {
        return title;
    }

    public long getOriginalPrice() {
        return originalPrice;
    }

    public long getNowPrice() {
        return nowPrice;
    }

    public String getTagName() {
        return tagName;
    }

    public int getStatus() {
        return status;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public String getDescribe() {
        return describe;
    }

    public boolean isReleaseCheck() {
        return releaseCheck;
    }
}
