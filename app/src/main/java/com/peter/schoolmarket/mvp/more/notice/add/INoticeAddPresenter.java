package com.peter.schoolmarket.mvp.more.notice.add;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by PetterChen on 2017/5/7.
 */

public interface INoticeAddPresenter {
    void releaseNotice(EditText title, EditText content);//调用该方法正式发布notice
}
