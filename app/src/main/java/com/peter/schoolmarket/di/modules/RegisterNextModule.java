package com.peter.schoolmarket.di.modules;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.peter.schoolmarket.mvp.login.register.next.IRegisterNextPresenter;
import com.peter.schoolmarket.mvp.login.register.next.IRegisterNextView;
import com.peter.schoolmarket.mvp.login.register.next.RegisterNextPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by PetterChen on 2017/4/19.
 */

@Module
public class RegisterNextModule {

    private Context context;
    private IRegisterNextView iRegisterNextView;

    public RegisterNextModule(Context context, IRegisterNextView iRegisterNextView) {
        this.context = context;
        this.iRegisterNextView = iRegisterNextView;
    }

    @Singleton @Provides
    public IRegisterNextPresenter provideIRegisterNextPresenter() {
        return new RegisterNextPresenter(context, iRegisterNextView);
    }

    @Singleton @Provides
    public MaterialDialog provideRegisterNextProgress() {
        return new MaterialDialog.Builder(context)
                .content("正在添加手机号码...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)//是否水平放置
                .title("请稍等")
                .build();
    }
}
