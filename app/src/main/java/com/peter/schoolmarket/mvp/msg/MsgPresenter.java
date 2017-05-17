package com.peter.schoolmarket.mvp.msg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.adapter.recycler.RecyclerViewHolder;
import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.pojo.Msg;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.network.RetrofitConf;
import com.peter.schoolmarket.util.MsgUtil;
import com.peter.schoolmarket.util.TimeUtils;

import java.net.Socket;
import java.util.List;

import dagger.Module;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by PetterChen on 2017/5/6.
 */

public class MsgPresenter implements IMsgPresenter, IMsgListener {
    private IMsgModel model;
    private IMsgView view;
    private Context context;
    private int userId;
    private Realm realm;

    MsgPresenter(IMsgView view, Context context, Realm realm) {
        model = new MsgModel();
        this.view = view;
        this.context = context;
        this.realm = realm;
        userId = LoginInfoExecutor.getUser(context).getId();
    }

    @Override
    public void refresh() {
        model.msgDataReq(this, realm, userId, context);
    }

    @Override
    public void init() {
        RealmQuery<Msg> query =  realm.where(Msg.class);
        RealmResults<Msg> results = query.findAll();
        List<Msg> data =  realm.copyFromRealm(results);

        if (data.size()>0){
            initList(data);
        }else {
            //这里可以添加一个加载窗口
            view.showProgress();
            model.msgDataReq(this, realm, userId, context);
        }
        //initList(MsgUtil.getMsgs());
    }

    private void initList(final List<Msg> msgs) {
        RecyclerCommonAdapter<?> adapter=new RecyclerCommonAdapter<Msg>(context,msgs, R.layout.message_item) {
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
                        Toast.makeText(context, "jump", Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        view.loadDataSuccess(adapter);
    }

    @Override
    public void updateList() {
        RealmQuery<Msg> query =  realm.where(Msg.class);
        RealmResults<Msg> results = query.findAll();
        List<Msg> data =  realm.copyFromRealm(results);
        if (data.size()>0){
            initList(data);
        }
    }

    @Override
    public void msgReqComplete(boolean flag) {
        view.hideRefresh();
        view.hideProgress();
        if (flag) {
            Intent intent = new Intent(MsgFragment.MY_BROADCAST_ACTION);
            context.sendBroadcast(intent);
        }
    }
}
