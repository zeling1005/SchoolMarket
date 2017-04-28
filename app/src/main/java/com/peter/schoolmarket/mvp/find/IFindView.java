package com.peter.schoolmarket.mvp.find;

import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;

/**
 * Created by PetterChen on 2017/4/28.
 */

public interface IFindView {
    void loadDataSuccess(RecyclerCommonAdapter<?> adapter);//当数据获取成功
    void hideRefresh();//隐藏下拉刷新
}
