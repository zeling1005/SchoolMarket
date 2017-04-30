package com.peter.schoolmarket.mvp.trade.add;

import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by PetterChen on 2017/4/30.
 */

public interface ITradeAddPresenter {
    void releaseTrade(String picUrls, EditText titleContent, EditText nowPrice,
                      EditText originalPrice, EditText descContent, TextView tag);//调用该方法正式发布商品
}
