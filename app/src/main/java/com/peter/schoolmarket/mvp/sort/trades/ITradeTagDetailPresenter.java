package com.peter.schoolmarket.mvp.sort.trades;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

/**
 * Created by PetterChen on 2017/4/29.
 */

public interface ITradeTagDetailPresenter {
    void init(String tagName);
    void refresh();
    void loadNextPage();
    void loadSearchPage(String query);
}
