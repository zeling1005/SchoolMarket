package com.peter.schoolmarket.mvp.msg;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.adapter.recycler.RecyclerViewHolder;

import java.net.Socket;
import java.util.List;

import dagger.Module;
import io.realm.Realm;

/**
 * Created by PetterChen on 2017/5/6.
 */

public class MsgPresenter implements IMsgPresenter, IMsgListener {
    private IMsgModel model;
    private IMsgView view;
    private Context context;

    MsgPresenter(IMsgView view, Context context) {
        model = new MsgModel();
        this.view = view;
        this.context = context;
    }



    @Override
    public void refresh(Realm realm) {

    }

    @Override
    public void init(Realm realm) {

    }
}
