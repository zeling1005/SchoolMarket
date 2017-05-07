package com.peter.schoolmarket.mvp.more.notice.add;

import com.peter.schoolmarket.data.dto.Result;

/**
 * Created by PetterChen on 2017/5/7.
 */

public interface INoticeAddListener {
    void addNoticeResult(Result<String> result);//执行发布notice请求，得到请求结果
}
