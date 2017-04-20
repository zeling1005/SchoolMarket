package com.peter.schoolmarket.mvp.main;

import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

/**
 * Created by PetterChen on 2017/4/20.
 */

public interface IMainPresenter {
    void initMain(MaterialSearchView searchView, View header);//初始化
    void sideJump(int id);//侧边栏点击跳转
}
