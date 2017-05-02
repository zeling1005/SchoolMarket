package com.peter.schoolmarket.mvp.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.mvp.base.BaseFragment;

/**
 * Created by PetterChen on 2017/5/2.
 */

public class TestFragment extends BaseFragment {
    TextView text;

    @Override
    protected int getLayoutResId() {
        return R.layout.test_fragment;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        text = (TextView) view.findViewById(R.id.test_fragment_text);
        Bundle bundle = getArguments();
        text.setText(bundle.getString("textString"));
        /*FragmentA a = new FragmentA();
        Bundle bundle = new Bundle();
        a.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, a).commit();*/
    }
}
