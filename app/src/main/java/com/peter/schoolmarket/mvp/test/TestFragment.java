package com.peter.schoolmarket.mvp.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.DividerItemNormalDecoration;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.adapter.recycler.RecyclerViewHolder;
import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.pojo.Msg;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.mvp.base.BaseFragment;
import com.peter.schoolmarket.mvp.trade.detail.TradeDetailActivity;
import com.peter.schoolmarket.network.RetrofitConf;
import com.peter.schoolmarket.util.MsgUtil;
import com.peter.schoolmarket.util.TimeUtils;

import java.util.List;

/**
 * Created by PetterChen on 2017/5/2.
 */

public class TestFragment extends BaseFragment {
    //TextView text;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected int getLayoutResId() {
        //return R.layout.test_fragment;
        return R.layout.message_fragment;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.test_msg_list);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.test_msg_refresh);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //presenter.refreshView();
                Toast.makeText(getActivity(), "refresh", Toast.LENGTH_SHORT).show();
            }
        });

        List<Msg> msgs = MsgUtil.getMsgs();
        initList(msgs);

        /*text = (TextView) view.findViewById(R.id.test_fragment_text);
        Bundle bundle = getArguments();
        text.setText(bundle.getString("textString"));*/
        /*FragmentA a = new FragmentA();
        Bundle bundle = new Bundle();
        a.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, a).commit();*/
    }

    public void initList(final List<Msg> msgs) {
        RecyclerCommonAdapter<?> adapter=new RecyclerCommonAdapter<Msg>(getActivity(),msgs, R.layout.message_item) {
            @Override
            public void convert(RecyclerViewHolder holder, Msg item) {
                holder.setText(R.id.test_msg_title,item.getTitle());
                holder.setText(R.id.test_msg_content,item.getContent());
                String time = TimeUtils.getDate(item.getCreateTime());
                holder.setText(R.id.test_msg_time,time);
                holder.setOnClickListener(R.id.test_item_name, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转
                        Toast.makeText(getActivity(), "jump", Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
