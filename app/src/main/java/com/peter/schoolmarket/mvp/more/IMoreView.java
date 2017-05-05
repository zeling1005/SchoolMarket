package com.peter.schoolmarket.mvp.more;

import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;

/**
 * Created by PetterChen on 2017/5/5.
 */

public interface IMoreView {
    void loadDataSuccess(RecyclerCommonAdapter<?> adapter);//当数据获取成功
    void hideRefresh();//隐藏下拉刷新
    void showProgress();
    void hideProgress();
}
