package com.peter.schoolmarket.mvp.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.load.model.ModelCache;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.mvp.find.FindFragment;
import com.peter.schoolmarket.mvp.login.LoginActivity;
import com.peter.schoolmarket.mvp.main.setting.SettingActivity;
import com.peter.schoolmarket.mvp.main.trade.DrawerTradeActivity;
import com.peter.schoolmarket.mvp.more.MoreFragment;
import com.peter.schoolmarket.mvp.sort.TradeSortFragment;
import com.peter.schoolmarket.mvp.test.TestActivity;
import com.peter.schoolmarket.mvp.test.TestFragment;
import com.peter.schoolmarket.network.RetrofitConf;
import com.peter.schoolmarket.util.ResultInterceptor;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by PetterChen on 2017/4/20.
 */

class MainPresenter implements IMainPresenter, IMainListener {

    private AppCompatActivity context;
    private IMainModel model;
    private FragmentManager fm;
    private TextView toolbarTitle;
    private BottomNavigationBar bottomNavigationBar;
    private TestFragment testFragment;
    private FindFragment findFragment;
    private MoreFragment moreFragment;
    private TradeSortFragment tradeSortFragment;
    private IMainView view;
    //private Realm realmDefault;

    MainPresenter(AppCompatActivity context, IMainView view) {
        this.context = context;
        this.view = view;
        model = new MainModel();
        //this.realmDefault=realm;
        fm = context.getFragmentManager();
        toolbarTitle = (TextView)context.findViewById(R.id.toolbar_title);
        bottomNavigationBar = (BottomNavigationBar)context.findViewById(R.id.bottom_navigation_bar);
    }

    @Override
    public void initMain(View header, Realm realm) {
        initHeader(header);
        initBottomMenu();
        initUsers(realm);
    }

    private void initUsers(Realm realm) {
        model.getUsers(this, realm);
    }

    private void initHeader(View headerLayout) {
        User user= LoginInfoExecutor.getUser(context);
        SimpleDraweeView avatarUrl=(SimpleDraweeView) headerLayout.findViewById(R.id.header_portrait);
        TextView username=(TextView) headerLayout.findViewById(R.id.header_user_name);
        TextView phone=(TextView) headerLayout.findViewById(R.id.header_phone);
        if (!(user.getImgUrl().isEmpty())){
            avatarUrl.setImageURI(Uri.parse(AppConf.BASE_URL + RetrofitConf.base_img + user.getImgUrl()));
        }
        username.setText(user.getUsername());
        phone.setText(user.getPhone());
    }

    @Override
    public void sideJump(int id) {
        //根据点击按钮设置跳转
        String type = "";
        switch (id){
            case R.id.nav_buying:
                type = "待确认";
                break;
            case R.id.nav_bought:
                type = "已买";
                break;
            case R.id.nav_selling:
                type = "正在卖";
                break;
            case R.id.nav_sold:
                type = "已卖";
                break;
            case R.id.nav_share:
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                /*textIntent.setPackage("com.tencent.mobileqq");
                textIntent.setPackage("com.tencent.mm");
                textIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");//微信朋友
                textIntent.setClassName("com.tencent.mobileqq", "cooperation.qqfav.widget.QfavJumpActivity");//保存到QQ收藏
                textIntent.setClassName("com.tencent.mobileqq", "cooperation.qlink.QlinkShareJumpActivity");//QQ面对面快传
                textIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.qfileJumpActivity");//传给我的电脑
                textIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");//QQ好友或QQ群
                textIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");//微信朋友圈，仅支持分享图片*/
                textIntent.putExtra(Intent.EXTRA_TEXT, "这里以后替换成app下载地址");
                context.startActivity(Intent.createChooser(textIntent, "分享"));
                break;
            case R.id.nav_setting:
                Intent intent = new Intent(context, SettingActivity.class);
                context.startActivity(intent);
                break;
            case R.id.nav_exit:
                logout();
                break;
            default:
                break;
        }
        if (!type.equals("")){
            /*Intent tradeIntent=new Intent(context, DrawerTradeActivity.class);
            tradeIntent.putExtra("title",type);
            context.startActivity(tradeIntent);*/
            Intent intent=new Intent(context, TestActivity.class);
            intent.putExtra("textString",type);
            context.startActivity(intent);
        }
    }

    private void initBottomMenu() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);//此模式显示文字
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)//点击无水波纹，可以设置为Item添加Badge
                .addItem(new BottomNavigationItem(R.drawable.main_bottom_find, "首页"))
                .addItem(new BottomNavigationItem(R.drawable.main_bottom_trade, "闲货"))
                //.addItem(new BottomNavigationItem(R.drawable.main_bottom_wish, "求购"))
                .addItem(new BottomNavigationItem(R.drawable.main_bottom_msg, "消息"))
                .addItem(new BottomNavigationItem(R.drawable.main_bottom_more, "更多"))
                .setActiveColor(R.color.black)
                .setInActiveColor(R.color.theme_primaryDark)
                .setFirstSelectedPosition(0)
                .initialise();

        toolbarTitle.setText("首页");
        showFragment(0);

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {//这里也可以使用SimpleOnTabSelectedListener
            @Override
            public void onTabSelected(int position) {//未选中 -> 选中
                switch (position) {
                    case 0:
                        toolbarTitle.setText("首页");
                        showFragment(0);
                        //此处设置点击后的事件，设置标题栏和fragment
                        break;
                    case 1:
                        toolbarTitle.setText("闲货");
                        showFragment(1);
                        break;
                    case 2:
                        toolbarTitle.setText("消息");
                        showFragment(2);
                        break;
                    case 3:
                        toolbarTitle.setText("更多");
                        showFragment(3);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {//选中 -> 未选中
            }

            @Override
            public void onTabReselected(int position) {//选中 -> 选中
            }
        });
    }

    private void showFragment(int position) {
        int type = -1;
        FragmentTransaction ft = fm.beginTransaction();
        hideAllFragment(ft);
        switch (position) {
            case 0 : if (findFragment != null) {
                ft.show(findFragment);
            } else {
                findFragment = new FindFragment();
                ft.add(R.id.frame_layout, findFragment);
            }
                break;
            case 1 : if (tradeSortFragment != null) {
                ft.show(tradeSortFragment);
            } else {
                tradeSortFragment = new TradeSortFragment();
                ft.add(R.id.frame_layout, tradeSortFragment);
            }
                /*if (testFragment != null) {
                    ft.show(testFragment);
                } else {
                    testFragment = new TestFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("textString", "");
                    testFragment.setArguments(bundle);
                    ft.add(R.id.frame_layout, testFragment);
                }*/
                break;
            case 2 : if (testFragment != null) {
                ft.show(testFragment);
            } else {
                testFragment = new TestFragment();
                Bundle bundle = new Bundle();
                bundle.putString("textString", "");
                testFragment.setArguments(bundle);
                ft.add(R.id.frame_layout, testFragment);
            }
                break;
            case 3 : /*if (moreFragment != null) {
                ft.show(moreFragment);
            } else {
                moreFragment = new MoreFragment();
                ft.add(R.id.frame_layout, moreFragment);
            }*/
                if (testFragment != null) {
                    ft.show(testFragment);
                } else {
                    testFragment = new TestFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("textString", "");
                    testFragment.setArguments(bundle);
                    ft.add(R.id.frame_layout, testFragment);
                }
                break;
            default:
                break;
        }
        ft.commit();
    }

    private void hideAllFragment(FragmentTransaction ft) {
        if (testFragment != null) {
            ft.hide(testFragment);
        }
        if (findFragment != null) {
            ft.hide(findFragment);
        }
        if (tradeSortFragment != null) {
            ft.hide(tradeSortFragment);
        }
        if (moreFragment != null) {
            ft.hide(moreFragment);
        }
    }

    private void logout(){
        LoginInfoExecutor.logOut(context);
        Intent loginIntent=new Intent(context, LoginActivity.class);
        context.startActivity(loginIntent);
        context.finish();
    }

    @Override
    public void onUsersReqComplete(Result<List<User>> result, Realm realm) {
        if (!ResultInterceptor.instance.resultDataHandler(result)){//判断是否Result数据为空
            return;
        }
        final List<User> userList = result.getData();
        final RealmResults<User> results = realm.where(User.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {//清空数据
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
        realm.executeTransactionAsync(new Realm.Transaction() {//重新加载数据
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(userList);
            }
        });
    }
}
