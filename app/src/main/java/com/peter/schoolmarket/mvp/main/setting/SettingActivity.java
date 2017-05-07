package com.peter.schoolmarket.mvp.main.setting;

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

public class SettingActivity extends BaseActivity {
    SimpleDraweeView back;
    LinearLayout person;
    LinearLayout about;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.setting_activity);
        initVariate();
        manageVariate();
    }

    private void initVariate() {
        back = (SimpleDraweeView) findViewById(R.id.setting_toolbar_back);
        person = (LinearLayout) findViewById(R.id.setting_person);
        about = (LinearLayout) findViewById(R.id.setting_about);
    }

    private void manageVariate() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "个人资料", Toast.LENGTH_SHORT).show();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(SettingActivity.this, "关于闲货", Toast.LENGTH_SHORT).show();
                new MaterialDialog.Builder(SettingActivity.this)
                        .title("闲货")
                        .content("闲货是一款给高校师生们提供一个二手交易平台的手机应用，有任何疑问请发送" +
                                "邮件到neu20133788@163.com，谢谢！")
                        .positiveText("确定")
                        .show();
            }
        });
    }
}
