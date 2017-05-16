package com.peter.schoolmarket.mvp.main;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/4/20.
 */

public interface IMainPresenter {
    void initMain(View header, Realm realm);//初始化
    void sideJump(int id);//侧边栏点击跳转
}
