package com.peter.schoolmarket.mvp.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.mvp.base.BaseActivity;

/**
 * Created by PetterChen on 2017/5/2.
 */

public class TestActivity extends BaseActivity {
    TextView text;
    TextView title;
    ImageView back;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.test_activity);

        //设置标题栏
        //toolbar = (Toolbar) findViewById(R.id.test_toolbar);
        //toolbar.setTitle(this.getResources().getString(R.string.main_title));
        //setSupportActionBar(toolbar);

        text = (TextView) findViewById(R.id.test_text);
        title = (TextView) findViewById(R.id.test_toolbar_title);
        back = (ImageView) findViewById(R.id.test_toolbar_back);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        Trade trade = (Trade) bundle.getSerializable("trade");
        text.setText(trade.getAuthorName());
        /*Intent intent = new Intent(this, TwoActivity.class);
        intent.putExtra("key", "value");
        startActivity(intent);*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (TestActivity.this).finish();
            }
        });
    }
}
