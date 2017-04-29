package com.peter.schoolmarket.mvp.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.schoolmarket.application.MarketApp;

/**
 * Created by PetterChen on 2017/4/18.
 */

public abstract  class BaseFragment extends Fragment {

    //private Activity activity;

    protected abstract int getLayoutResId();

    protected abstract void initViews(View view, Bundle savedInstanceState);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        initViews(view, savedInstanceState);
        return view;
    }

    /*public Context getContext(){
        if(activity == null){
            return MarketApp.getInstance();
        }
        return activity;
    }*/

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity = getActivity();
    }*/
}