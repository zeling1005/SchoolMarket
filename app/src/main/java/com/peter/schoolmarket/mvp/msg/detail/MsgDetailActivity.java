package com.peter.schoolmarket.mvp.msg.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.data.pojo.Msg;
import com.peter.schoolmarket.mvp.base.BaseActivity;
import com.peter.schoolmarket.util.TimeUtils;

/**
 * Created by PetterChen on 2017/5/17.
 */

public class MsgDetailActivity extends BaseActivity {
    private SimpleDraweeView back;
    private TextView title;
    private TextView time;
    private TextView content;
    private Msg msg;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.msg_detail_activity);
        initVariate();
        manageVariate();
    }

    private void initVariate() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        msg = (Msg) bundle.getSerializable("msg");
        back = (SimpleDraweeView) findViewById(R.id.msg_detail_toolbar_back);
        title = (TextView) findViewById(R.id.msg_detail_title);
        time = (TextView) findViewById(R.id.msg_detail_time);
        content = (TextView) findViewById(R.id.msg_detail_content);
    }

    private void manageVariate() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (MsgDetailActivity.this).finish();
            }
        });
        title.setText(msg.getTitle());
        String tem = TimeUtils.getDate(msg.getCreateTime());
        time.setText(tem);
        content.setText(msg.getContent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}