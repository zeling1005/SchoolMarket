package com.peter.schoolmarket.application;

/**
 * Created by PetterChen on 2017/4/13.
 */

public interface AppConf {

    boolean useMock=true;//是否使用本地离线数据，不与服务器进行交互；true表示使用离线，false使用服务器数据

    String IP = "127.0.0.1";//服务器地址

    int PORT = 8080;//服务器端口号

    String BASE_URL="http://118.202.41.83:8080/";//服务器的地址及端口118.202.41.83

    long Msg_Interval=30L;//轮询获取消息的时间间隔.(秒)

    int size=8;//每个页面加载的数据长度

}