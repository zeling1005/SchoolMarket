package com.peter.schoolmarket.mvp.msg;

import java.net.Socket;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/5/6.
 */

public interface IMsgPresenter {
    void refresh(Realm realm);//刷新功能，但是好像不太需要，因为消息是实时的,断网之后重新连接还是需要的
    void init(Realm realm);//初始化消息数据
}
