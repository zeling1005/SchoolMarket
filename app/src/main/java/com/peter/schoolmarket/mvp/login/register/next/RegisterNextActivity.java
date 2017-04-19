package com.peter.schoolmarket.mvp.login.register.next;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.di.components.DaggerRegisterNextComponent;
import com.peter.schoolmarket.di.modules.RegisterNextModule;
import com.peter.schoolmarket.mvp.base.BaseActivity;
import com.peter.schoolmarket.mvp.login.LoginActivity;
import com.peter.schoolmarket.mvp.main.MainActivity;

import javax.inject.Inject;

/**
 * Created by PetterChen on 2017/4/19.
 */

public class RegisterNextActivity extends BaseActivity implements IRegisterNextView {

    @Inject
    IRegisterNextPresenter iRegisterNextPresenter;

    @Inject
    MaterialDialog registerNextProgress;

    TextInputLayout phoneLayout;
    //EditText phoneText;
    TextView registerNextSub;
    String userId;
    String phone;


    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.register_next_activity);
        initValiable();
    }

    private void initValiable() {
        DaggerRegisterNextComponent
                .builder()
                .registerNextModule(new RegisterNextModule(this, this))
                .build()
                .inject(this);
        phoneLayout = (TextInputLayout) findViewById(R.id.register_next_phone);
        //phoneText = (EditText) findViewById(R.id.register_next_phone_text);
        registerNextSub = (TextView) findViewById(R.id.register_next_sub);
        Intent intent=getIntent();
        userId=intent.getStringExtra("userId");
        registerNextSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = phoneLayout.getEditText().getText().toString();
                if (phone.isEmpty()){
                    phoneLayout.setErrorEnabled(true);
                    phoneLayout.setError("用户名不可以为空");
                }else {
                    iRegisterNextPresenter.addPhoneNum(phone);
                }
            }
        });


    }

    @Override
    public String getPhoneNumber() {
        if (phone.isEmpty()){
            return null;
        }
        return phone;
    }

    @Override
    public void showLoading() {
        registerNextProgress.show();
    }

    @Override
    public void hideLoading() {
        registerNextProgress.dismiss();
    }

    @Override
    public void addSuccess() {
        Intent intent = new Intent(RegisterNextActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void addFailed() {
        new SuperToast(RegisterNextActivity.this)
                .setText("手机号码添加失败")
                .setDuration(Style.DURATION_LONG)
                .setColor(PaletteUtils.getTransparentColor(PaletteUtils.MATERIAL_RED))
                .setAnimations(Style.ANIMATIONS_POP)
                .show();
    }
}
