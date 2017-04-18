package com.peter.schoolmarket.mvp.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by PetterChen on 2017/4/11.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = "BaseActivity";
    public abstract void initViews(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        initViews(savedInstanceState);
    }

}
