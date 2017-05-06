package com.peter.schoolmarket.mvp.more.notice.add;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.mvp.base.BaseActivity;

/**
 * Created by PetterChen on 2017/5/7.
 */

public class NoticeAddActivity extends BaseActivity {
    SimpleDraweeView cancel;
    SimpleDraweeView submit;
    EditText headline;
    EditText content;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.notice_add_activity);
        initVariate();
        manageVariate();
    }

    private void initVariate() {
        cancel = (SimpleDraweeView) findViewById(R.id.notice_add_cancel);
        submit = (SimpleDraweeView) findViewById(R.id.notice_add_submit);
        headline = (EditText) findViewById(R.id.notice_add_title);
        content = (EditText) findViewById(R.id.notice_add_content);
    }

    private void manageVariate() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (NoticeAddActivity.this).finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布notice
                Toast.makeText(NoticeAddActivity.this, "进行发布操作", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
