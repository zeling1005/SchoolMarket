package com.peter.schoolmarket.mvp.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.mvp.base.BaseActivity;

/**
 * Created by PetterChen on 2017/4/6.
 */

public class LoginActivity extends BaseActivity {

    @Override
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.login_activity);
    }
}
