package com.peter.schoolmarket.mvp.main.trade;

/**
 * Created by PetterChen on 2017/5/7.
 */

public interface IDrawerTradePresenter {
    void init(int typeId);
    void refresh();
    void loadNextPage();
}
