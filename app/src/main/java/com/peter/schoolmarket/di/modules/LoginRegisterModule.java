package com.peter.schoolmarket.di.modules;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.peter.schoolmarket.di.qualifiers.LoginProgress;
import com.peter.schoolmarket.di.qualifiers.RegisterProgress;
import com.peter.schoolmarket.mvp.login.ILoginRegisterPresenter;
import com.peter.schoolmarket.mvp.login.ILoginRegisterView;
import com.peter.schoolmarket.mvp.login.LoginRegisterPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by PetterChen on 2017/4/19.
 */

@Module
public class LoginRegisterModule {

    private ILoginRegisterView loginRegisterView;
    private Context context;

    public LoginRegisterModule(ILoginRegisterView view, Context context) {
        this.loginRegisterView = view;
        this.context = context;
    }

    @Singleton @Provides
    public ILoginRegisterPresenter provideILoginRegisterPresenter() {
        return new LoginRegisterPresenter(loginRegisterView, context);
    }

    @LoginProgress
    @Singleton @Provides
    public MaterialDialog provideLoginProgress() {
        return new MaterialDialog.Builder(context)
                .content("正在登录...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)//是否水平放置
                .title("请稍等")
                .build();
    }

    @RegisterProgress
    @Singleton @Provides
    public MaterialDialog provideRegisterProgress() {
        return new MaterialDialog.Builder(context)
                .content("正在注册...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)//是否水平放置
                .title("请稍等")
                .build();
    }
}
