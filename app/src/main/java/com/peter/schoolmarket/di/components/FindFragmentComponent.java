package com.peter.schoolmarket.di.components;

import com.peter.schoolmarket.di.modules.FindFragmentModule;
import com.peter.schoolmarket.mvp.find.FindFragment;
import com.peter.schoolmarket.mvp.find.IFindPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by PetterChen on 2017/4/28.
 */

@Singleton
@Component(modules = FindFragmentModule.class)
public interface FindFragmentComponent {
    IFindPresenter provideIFindPresenter();

    void inject(FindFragment fragment);
}
