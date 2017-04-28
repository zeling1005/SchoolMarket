package com.peter.schoolmarket.di.modules;

import android.content.Context;

import com.peter.schoolmarket.mvp.find.FindPresenter;
import com.peter.schoolmarket.mvp.find.IFindPresenter;
import com.peter.schoolmarket.mvp.find.IFindView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by PetterChen on 2017/4/28.
 */

@Module
public class FindFragmentModule {

    Context context;
    IFindView view;

    public FindFragmentModule(Context context, IFindView view) {
        this.context = context;
        this.view = view;
    }

    @Singleton
    @Provides
    public IFindPresenter provideIFindPresenter() {
        return new FindPresenter(context, view);
    }
}
