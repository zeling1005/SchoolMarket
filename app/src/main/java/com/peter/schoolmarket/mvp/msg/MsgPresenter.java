package com.peter.schoolmarket.mvp.msg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.adapter.recycler.RecyclerViewHolder;
import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.pojo.Msg;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.mvp.msg.detail.MsgDetailActivity;
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
        Log.d("getMsgsmsgs", "initList()");
        RecyclerCommonAdapter<?> adapter=new RecyclerCommonAdapter<Msg>(context,msgs, R.layout.message_item) {
            @Override
            public void convert(RecyclerViewHolder holder, final Msg item) {
                holder.setText(R.id.test_msg_title,item.getTitle());
                holder.setText(R.id.test_msg_content,item.getContent());
                String time = TimeUtils.getDate(item.getCreateTime());
                holder.setText(R.id.test_msg_time,time);
                holder.setOnClickListener(R.id.test_item_name, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转
                        //Toast.makeText(context, "jump", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(context, MsgDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("msg", item);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }
        };
        view.loadDataSuccess(adapter);
    }

    @Override
    public void updateList() {
        //Log.d("getMsgsmsgs", "updateList()");
        RealmQuery<Msg> query =  realm.where(Msg.class);
        RealmResults<Msg> results = query.findAll();
        List<Msg> data =  realm.copyFromRealm(results);
        if (data.size()>0){
            //Log.d("getMsgsmsgs", "size()>0");
            initList(data);
        } else {
            Msg msg = new Msg();
            msg.setTitle("您未收到任何消息！");
            msg.setContent("去淘淘别人家的闲货如何？");
            msg.setCreateTime(System.currentTimeMillis());
            data.add(msg);
            initList(data);
        }
        //Log.d("getMsgsmsgs", "size()<0");
    }

    @Override
    public void msgReqComplete(boolean flag) {
        view.hideRefresh();
        view.hideProgress();
        /*if (flag) {
            Log.d("getMsgsmsgs", "Presenter发送广播");
            Intent intent = new Intent(MsgFragment.MY_BROADCAST_ACTION);
            context.sendBroadcast(intent);
        }*/
        Intent intent = new Intent(MsgFragment.MY_BROADCAST_ACTION);
        context.sendBroadcast(intent);
    }
}
