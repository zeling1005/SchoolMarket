package com.peter.schoolmarket.mvp.more.notice.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.mvp.base.BaseActivity;

/**
 * Created by PetterChen on 2017/5/7.
 */

public class NoticeAddActivity extends BaseActivity implements INoticeAddView {
    SimpleDraweeView cancel;
    SimpleDraweeView submit;
    EditText headline;
    EditText content;
    MaterialDialog progress;
    NoticeAddPresenter presenter;

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
        progress = new MaterialDialog.Builder(NoticeAddActivity.this)
                .content("正在处理...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)//是否水平进度条
                .title("请稍等")
                .build();
        presenter = new NoticeAddPresenter(this, this);
    }

    private void manageVariate() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(NoticeAddActivity.this)
                        .title("放弃发布？")
                        .content("您正在发布公告，离开界面会导致发布失败！确定离开？")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                (NoticeAddActivity.this).finish();
                            }
                        })
                        .show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布notice
                //Toast.makeText(NoticeAddActivity.this, "进行发布操作", Toast.LENGTH_SHORT).show();
                presenter.releaseNotice(headline, content);
            }
        });
    }

    @Override
    public void noticeAddSuccess() {
        Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public void whenFail(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        if (!progress.isShowing()){
            progress.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progress.isShowing()) {
            progress.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(NoticeAddActivity.this)
                .title("放弃发布？")
                .content("您正在发布公告，离开界面会导致发布失败！确定离开？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        (NoticeAddActivity.this).finish();
                    }
                })
                .show();
    }
}
