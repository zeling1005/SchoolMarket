package com.peter.schoolmarket.mvp.msg;

/**
 * Created by PetterChen on 2017/5/6.
 */

public class MsgModel implements IMsgModel {
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //在子线程中初始化Socket对象
                    socket=new Socket(AppConf.IP, AppConf.PORT);
                    //socket.setSoTimeout(5000);
                    //根据clientSocket.getInputStream得到BufferedReader对象，从而从输入流中获取数据
                    BufferedReader mReader=new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                    while(isReceivingMsgReady){
                        if(mReader.ready()){
                            Message msg = handler.obtainMessage();
                            msg.what=2;
                            msg.obj=mReader.readLine();
                            handler.sendMessage(msg);
                        }
                        Thread.sleep(200);
                    }
                    mReader.close();
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();*/

    /*private final MyHandler mHandler = new MyHandler((MainActivity)getActivity());
    static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        *//*String msgContent=(String) msg.obj;
                        sb.append("我："+msgContent+"\n");
                        tvContent.setText(sb.toString());*//*
                        break;
                    case 2:
                        String json = (String) msg.obj;
                        Result<Msg> result = new Gson().fromJson(json, new TypeToken<Result<Msg>>(){}.getType());
                        if (ResultInterceptor.instance.resultHandler(result)) {
                            if (result.getCode() == 101) {
                                Toast.makeText(activity, "服务器连接成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Msg msg1 = result.getData();
                                msgs.add(msg1);
                            }
                        }
                        break;
                }
            }
        }
    }*/
}
