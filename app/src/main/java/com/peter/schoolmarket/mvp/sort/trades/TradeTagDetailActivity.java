package com.peter.schoolmarket.mvp.sort.trades;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.mvp.base.BaseActivity;
import com.peter.schoolmarket.mvp.main.MainActivity;

import java.util.ArrayList;

/**
 * Created by PetterChen on 2017/4/29.
 */

public class TradeTagDetailActivity extends BaseActivity implements ITradeTagDetailView {

    protected RecyclerView recyclerView;
    TradeTagDetailPresenter presenter;
    private String tagName;
    private MaterialSearchView searchView;
    private ImageView back;
    TextView title;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.trade_tag_detail_activity);

        recyclerView = (RecyclerView) findViewById(R.id.trade_tag_detail_list);
        presenter = new TradeTagDetailPresenter(this, this);
        back = (ImageView) findViewById(R.id.trade_tag_back);
        title = (TextView) findViewById(R.id.trade_tag_title);
        searchView = (MaterialSearchView) findViewById(R.id.trade_tag_search_view);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        tagName=bundle.getString("tagName");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TradeTagDetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        title.setText(tagName);
        recyclerView.setLayoutManager(new LinearLayoutManager(TradeTagDetailActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (tagName!=null){
            presenter.getTradeListByTag(tagName);
        }
    }

    //当客户点击MENU按钮的时候，调用该方法
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trade_tag_search_item, menu);
        MenuItem item = menu.findItem(R.id.trade_tag_search);
        searchView.setMenuItem(item);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void loadDataSuccess(RecyclerCommonAdapter<?> adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void loadDataFail(String errorMsg) {
        new SuperToast(TradeTagDetailActivity.this)
                .setText(errorMsg)
                .setDuration(Style.DURATION_LONG)
                .setColor(PaletteUtils.getTransparentColor(PaletteUtils.MATERIAL_RED))
                .setAnimations(Style.ANIMATIONS_POP)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

}
