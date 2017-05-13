package com.peter.schoolmarket.mvp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.di.components.DaggerLoginRegisterComponent;
import com.peter.schoolmarket.di.modules.LoginRegisterModule;
import com.peter.schoolmarket.di.qualifiers.LoginProgress;
import com.peter.schoolmarket.di.qualifiers.RegisterProgress;
import com.peter.schoolmarket.mvp.base.BaseActivity;
import com.peter.schoolmarket.mvp.login.register.next.RegisterNextActivity;
import com.peter.schoolmarket.mvp.main.MainActivity;

import javax.inject.Inject;

import shem.com.materiallogin.DefaultLoginView;
import shem.com.materiallogin.DefaultRegisterView;
import shem.com.materiallogin.MaterialLoginView;

/**
 * Created by PetterChen on 2017/4/6.
 */

public class LoginActivity extends BaseActivity implements ILoginRegisterView {
    @Inject
    ILoginRegisterPresenter iLoginRegisterPresenter;
    @LoginProgress
    @Inject
    MaterialDialog loginProgress;
    @RegisterProgress
    @Inject
    MaterialDialog registerProgress;
    MaterialLoginView loginRegister;

    private String username = "";
    private String password = "";


    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.login_activity);
        initVariate();
        manageVariate();
    }

    private void initVariate() {
        //使用依赖注入
        DaggerLoginRegisterComponent
                .builder()
                .loginRegisterModule(new LoginRegisterModule(this, this))
                .build()
                .inject(this);
        //登录-注册界面设置
        loginRegister = (MaterialLoginView) findViewById(R.id.login_activity);
    }

    private void manageVariate() {
        //登录界面
        ((DefaultLoginView)loginRegister.getLoginView()).setListener(new DefaultLoginView.DefaultLoginViewListener() {
            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                String name=loginUser.getEditText().getText().toString();
                String pass=loginPass.getEditText().getText().toString();
                if (name.isEmpty()){
                    loginUser.setErrorEnabled(true);
                    loginUser.setError("用户名不可以为空");
                }else if (pass.isEmpty()){
                    loginUser.setErrorEnabled(false);
                    loginPass.setErrorEnabled(true);
                    loginPass.setError("密码不可以为空");
                }else{
                    loginPass.setErrorEnabled(false);
                    loginUser.setErrorEnabled(false);
                    iLoginRegisterPresenter.login(name, pass);
                }
            }
        });
        //注册界面
        ((DefaultRegisterView)loginRegister.getRegisterView()).setListener(new DefaultRegisterView.DefaultRegisterViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
                String name=registerUser.getEditText().getText().toString();
                String pass=registerPass.getEditText().getText().toString();
                String repeatPass=registerPassRep.getEditText().getText().toString();
                if (name.isEmpty()){
                    registerUser.setErrorEnabled(true);
                    registerUser.setError("用户名不可以为空");
                }else if (pass.isEmpty()){
                    registerUser.setErrorEnabled(false);
                    registerPass.setErrorEnabled(true);
                    registerPass.setError("密码不可以为空");
                }else if (repeatPass.isEmpty()){
                    registerUser.setErrorEnabled(false);
                    registerPass.setErrorEnabled(false);
                    registerPassRep.setErrorEnabled(true);
                    registerPassRep.setError("请再一次输入密码");
                }else if (!pass.equals(repeatPass)){
                    registerUser.setErrorEnabled(false);
                    registerPass.setErrorEnabled(false);
                    registerPassRep.setErrorEnabled(true);
                    registerPassRep.setError("两次密码输入不一致");
                }else{
                    registerUser.setErrorEnabled(false);
                    registerPass.setErrorEnabled(false);
                    registerPassRep.setErrorEnabled(false);
                    username = name;
                    password = pass;
                    iLoginRegisterPresenter.register(name,pass);
                }
            }
        });
    }

    @Override
    public void showLoginLoading() {
        loginProgress.show();
    }

    @Override
    public void hideLoginLoading() {
        loginProgress.dismiss();
    }

    @Override
    public void showRegisterLoading() {
        registerProgress.show();
    }

    @Override
    public void hideRegisterLoading() {
        registerProgress.dismiss();
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerSuccess(String userId) {
        Intent intent=new Intent(LoginActivity.this, RegisterNextActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }

    @Override
    public void registerFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
