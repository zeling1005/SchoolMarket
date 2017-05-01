package com.peter.schoolmarket.mvp.splash;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.mvp.base.BaseActivity;
import com.peter.schoolmarket.mvp.login.LoginActivity;
import com.peter.schoolmarket.mvp.main.MainActivity;

/**
 * Created by PetterChen on 2017/4/11.
 */

public class SplashActivity extends BaseActivity {

    private SimpleDraweeView view;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //设置窗体全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);
        splashAction();
        initVariables();
    }

    protected void initVariables() {
        view = (SimpleDraweeView) findViewById(R.id.loading_gif);
        DraweeController builder = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(Uri.parse("res://drawable/" + R.drawable.splash_loading))
                .build();
        view.setController(builder);
    }

    private void splashAction() {
        //判断是否登录，登录则跳转主界面，否则到注册登录界面
        User user= LoginInfoExecutor.getUser(SplashActivity.this);
        if(user==null){
            //跳转到注册登录界面
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2500);
        }else {
            //跳转到主界面
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2500);
        }
    }
}
