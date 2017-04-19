package com.peter.schoolmarket.di.components;

import android.support.v4.media.VolumeProviderCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.peter.schoolmarket.di.modules.RegisterNextModule;
import com.peter.schoolmarket.mvp.login.register.next.IRegisterNextPresenter;
import com.peter.schoolmarket.mvp.login.register.next.RegisterNextActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by PetterChen on 2017/4/19.
 */

@Singleton
@Component(modules = RegisterNextModule.class)
public interface RegisterNextComponent {

    IRegisterNextPresenter provideIRegisterNextPresenter();
    MaterialDialog provideRegisterNextProgress();

    void inject(RegisterNextActivity activity);
}
