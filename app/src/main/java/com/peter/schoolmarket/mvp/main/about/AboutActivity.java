package com.peter.schoolmarket.mvp.main.about;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.mvp.base.BaseActivity;

/**
 * Created by PetterChen on 2017/5/7.
 */

public class AboutActivity extends BaseActivity {
    SimpleDraweeView back;
    LinearLayout feedback;
    LinearLayout help;
    LinearLayout version;
    LinearLayout introduce;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.about_activity);
        initVariate();
        manageVariate();
    }

    private void initVariate() {
        back = (SimpleDraweeView) findViewById(R.id.about_toolbar_back);
        version = (LinearLayout) findViewById(R.id.about_version);
        introduce = (LinearLayout) findViewById(R.id.about_introduce);
        help = (LinearLayout) findViewById(R.id.about_help);
        feedback = (LinearLayout) findViewById(R.id.about_feedback);
    }

    private void manageVariate() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.this.finish();
            }
        });
        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, "版本更新", Toast.LENGTH_SHORT).show();
            }
        });
        introduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, "功能介绍", Toast.LENGTH_SHORT).show();
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, "帮助", Toast.LENGTH_SHORT).show();
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, "反馈", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
