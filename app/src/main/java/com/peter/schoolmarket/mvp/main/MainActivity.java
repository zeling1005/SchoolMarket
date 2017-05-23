package com.peter.schoolmarket.mvp.main;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.mvp.base.BaseActivity;
import com.peter.schoolmarket.mvp.login.LoginActivity;
import com.peter.schoolmarket.mvp.msg.MsgService;
import com.peter.schoolmarket.util.PollingUtils;

import java.util.ArrayList;

import io.realm.Realm;

import static com.peter.schoolmarket.R.drawable.abc_ic_clear_material;

public class MainActivity extends BaseActivity implements IMainView{
    IMainPresenter presenter;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    SearchView mSearchView;
    //MaterialSearchView searchView;
    View headerLayout;
    private Realm realm;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity);
        initVariate();
        manageVariate();
        presenter.initMain(headerLayout, realm);
    }

    private void initVariate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        presenter = new MainPresenter(this, this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerLayout= navigationView.inflateHeaderView(R.layout.main_nav_header);
        mSearchView = (SearchView) findViewById(R.id.main_search);
        //searchView = (MaterialSearchView) findViewById(R.id.search_view);
        realm=Realm.getDefaultInstance();
        //PollingUtils.startPollingService(this, 5, MsgService.class, MsgService.ACTION);
    }

    private void manageVariate() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            //侧滑菜单点击事件
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();
                //设置跳转事件
                presenter.sideJump(id);
                return true;
            }
        });
        setSearchView();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }*/

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_search_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    public void hideSearch() {
        if (mSearchView.getVisibility() != View.GONE) {
            mSearchView.setVisibility(View.GONE);
        }
    }

    public void showSearch() {
        if (mSearchView.getVisibility() != View.VISIBLE) {
            mSearchView.setVisibility(View.VISIBLE);
        }
    }

    private void setSearchView() {
        mSearchView.setIconifiedByDefault(true);
        final int closeImgId = getResources().getIdentifier("search_close_btn", "id", getPackageName());
        ImageView closeImg = (ImageView) mSearchView.findViewById(closeImgId);
        if (closeImg != null) {
            closeImg.setImageResource(R.drawable.ic_search_cancel);
        }
        final int editViewId = getResources().getIdentifier("search_src_text", "id", getPackageName());
        SearchView.SearchAutoComplete mEdit = (SearchView.SearchAutoComplete) mSearchView.findViewById(editViewId);
        if (mEdit != null) {
            mEdit.setHintTextColor(getResources().getColor(R.color.text_color));
            mEdit.setTextColor(getResources().getColor(R.color.black));
            mEdit.setHint("Search...");
        }
        final int searchImgId = getResources().getIdentifier("search_button", "id", getPackageName());
        ImageView searchImg = (ImageView) mSearchView.findViewById(searchImgId);
        if (searchImg != null) {
            searchImg.setImageResource(R.drawable.ic_search);
        }
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(MainActivity.this, "shoushuo",Toast.LENGTH_SHORT).show();
                presenter.searchSubmit(query);
                /*if (!mSearchView.isIconified()) {
                    mSearchView.setIconified(true);
                }*/
                mSearchView.onActionViewCollapsed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        /*final float density = getResources().getDisplayMetrics().density;
        mSearchView.setIconified(false);
        mSearchView.setIconifiedByDefault(false);
        final int closeImgId = getResources().getIdentifier("search_close_btn", "id", getPackageName());
        ImageView closeImg = (ImageView) mSearchView.findViewById(closeImgId);
        if (closeImg != null) {
            LinearLayout.LayoutParams paramsImg = (LinearLayout.LayoutParams) closeImg.getLayoutParams();
            paramsImg.topMargin = (int) (-2 * density);
            closeImg.setImageResource(R.drawable.ic_search_cancel);
            closeImg.setLayoutParams(paramsImg);
        }
        final int editViewId = getResources().getIdentifier("search_src_text", "id", getPackageName());
        SearchView.SearchAutoComplete mEdit = (SearchView.SearchAutoComplete) mSearchView.findViewById(editViewId);
        if (mEdit != null) {
            mEdit.setHintTextColor(getResources().getColor(R.color.text_color));
            mEdit.setTextColor(getResources().getColor(R.color.black));
            //mEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            mEdit.setHint("Search...");
        }
        LinearLayout rootView = (LinearLayout) mSearchView.findViewById(R.id.search_bar);
        rootView.setBackgroundResource(R.drawable.edit_bg);
        rootView.setClickable(true);
        LinearLayout editLayout = (LinearLayout) mSearchView.findViewById(R.id.search_plate);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) editLayout.getLayoutParams();
        LinearLayout tipLayout = (LinearLayout) mSearchView.findViewById(R.id.search_edit_frame);
        LinearLayout.LayoutParams tipParams = (LinearLayout.LayoutParams) tipLayout.getLayoutParams();
        tipParams.leftMargin = 0;
        tipParams.rightMargin = 0;
        tipLayout.setLayoutParams(tipParams);
        ImageView icTip = (ImageView) mSearchView.findViewById(R.id.search_mag_icon);
        icTip.setImageResource(R.mipmap.ic_search_tip);
        params.topMargin = (int) (4 * density);
        editLayout.setLayoutParams(params);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchText = newText ;
                doSearch();
                return true;
            }
        });*/
    }

    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!mSearchView.isIconified()) {
            //Toast.makeText(MainActivity.this, "search shoushuo",Toast.LENGTH_SHORT).show();
            mSearchView.setIconified(true);
        } else {
            //Toast.makeText(MainActivity.this, "shoushuo",Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //PollingUtils.stopPollingService(this, MsgService.class, MsgService.ACTION);
        realm.close();
    }

    @Override
    public void hideDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void exit() {
        LoginInfoExecutor.logOut(this);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
        Intent loginIntent=new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}