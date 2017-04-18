package com.peter.schoolmarket.mvp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.mvp.base.BaseActivity;
import com.peter.schoolmarket.mvp.main.MainActivity;
import com.peter.schoolmarket.mvp.splash.SplashActivity;

import shem.com.materiallogin.DefaultLoginView;
import shem.com.materiallogin.DefaultRegisterView;
import shem.com.materiallogin.MaterialLoginView;

/**
 * Created by PetterChen on 2017/4/6.
 */

public class LoginActivity extends BaseActivity implements OnLoginListener {

    private UserModel userModel;


    @Override
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.login_activity);

        userModel = new UserModel();

        final MaterialLoginView loginRegister = (MaterialLoginView) findViewById(R.id.login_activity);

        ((DefaultLoginView)loginRegister.getLoginView()).setListener(new DefaultLoginView.DefaultLoginViewListener() {
            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                String name=loginUser.getEditText().getText().toString();
                String pass=loginPass.getEditText().getText().toString();
                if (name.isEmpty()){
                    loginUser.setErrorEnabled(true);
                    loginUser.setError("用户名不可以为空");
                }else if (pass.isEmpty()){
                    loginPass.setErrorEnabled(true);
                    loginPass.setError("密码不可以为空");
                }else{
                    userModel.login(name, pass, LoginActivity.this);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            }
        });
        ((DefaultRegisterView)loginRegister.getRegisterView()).setListener(new DefaultRegisterView.DefaultRegisterViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
                //
            }
        });
    }


    @Override
    public void loginSuccess(String message) {
        Toast.makeText(getApplicationContext(), "网络通信成功：" + message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(getApplicationContext(), "网络通信失败",
                Toast.LENGTH_SHORT).show();
    }
}
