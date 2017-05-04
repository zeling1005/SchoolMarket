package com.peter.schoolmarket.mvp.sort;

import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;

/**
 * Created by PetterChen on 2017/4/29.
 */

public interface ITradeSortView {

    //void jumpTradeDetail(String TagName);

    void loadTradeTagData(RecyclerCommonAdapter<?> adapter);
}
