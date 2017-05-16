package com.peter.schoolmarket.mvp.more.notice.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.pojo.Notice;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.mvp.base.BaseActivity;
import com.peter.schoolmarket.util.TimeUtils;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/5/6.
 */

public class NoticeDetailActivity extends BaseActivity {
    private SimpleDraweeView back;
    private TextView title;
    private TextView name;
    private TextView time;
    private TextView content;
    private Notice notice;
    private Realm realm;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.notice_detail_activity);
        initVariate();
        manageVariate();
    }

    private void initVariate() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        notice = (Notice) bundle.getSerializable("notice");
        back = (SimpleDraweeView) findViewById(R.id.notice_detail_toolbar_back);
        title = (TextView) findViewById(R.id.notice_detail_title);
        name = (TextView) findViewById(R.id.notice_detail_name);
        time = (TextView) findViewById(R.id.notice_detail_time);
        content = (TextView) findViewById(R.id.notice_detail_content);
        realm = Realm.getDefaultInstance();
    }

    private void manageVariate() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (NoticeDetailActivity.this).finish();
            }
        });
        title.setText(notice.getTitle());
        User user = realm.where(User.class).equalTo("id",notice.getAuthorId()).findFirst();
        if (user != null) {
            name.setText(user.getUsername());
        }
        String tem = TimeUtils.getDate(notice.getCreateTime());
        time.setText(tem);
        content.setText(notice.getContent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
