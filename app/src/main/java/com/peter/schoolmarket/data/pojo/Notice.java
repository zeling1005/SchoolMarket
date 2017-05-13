package com.peter.schoolmarket.data.pojo;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by PetterChen on 2017/5/5.
 */

public class Notice extends RealmObject implements Serializable {
    @PrimaryKey
    private int id;//N001
    private String authorName;
    private String authorImg;
    private String authorId;
    private String authorPhone;
    private String title;
    private String content;
    private long createTime;
    @Ignore
    private boolean releaseCheck;//为了在发布时方便检查数据完整性，不存储在数据库中

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthorPhone(String authorPhone) {
        this.authorPhone = authorPhone;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setReleaseCheck(boolean releaseCheck) {
        this.releaseCheck = releaseCheck;
    }

    public int getId() {
        return id;
    }

    public String getAuthorPhone() {
        return authorPhone;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public boolean isReleaseCheck() {
        return releaseCheck;
    }
}
