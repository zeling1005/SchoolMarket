package com.peter.schoolmarket.mvp.sort;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.DividerItemDecoration;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.mvp.base.BaseFragment;

/**
 * Created by PetterChen on 2017/4/29.
 */

public class TradeSortFragment extends BaseFragment implements ITradeSortView {

    private ITradeSortPresenter presenter;

    private RecyclerView recyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.sort_fragment;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.sort_grid);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        presenter.initView();
    }

    @Override
    public void jumpTradeDetail(String TagName) {
        /*Intent intent=new Intent(context,TradeDetailActivity.class);
        intent.putExtra("tradeId",item.getId());
        intent.putExtra("userId",item.getAuthorId());
        context.startActivity(intent);*/
    }

    @Override
    public void loadTradeTagData(RecyclerCommonAdapter<?> adapter) {
        recyclerView.setAdapter(adapter);
    }
}
