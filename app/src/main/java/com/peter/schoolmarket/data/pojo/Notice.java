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
    private int authorId;
    private String title;
    private String content;
    private long createTime;
    @Ignore
    private boolean releaseCheck;//为了在发布时方便检查数据完整性，不存储在数据库中

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isReleaseCheck() {
        return releaseCheck;
    }

    public void setReleaseCheck(boolean releaseCheck) {
        this.releaseCheck = releaseCheck;
    }
}
