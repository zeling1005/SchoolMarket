package com.peter.schoolmarket.util;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.data.pojo.TradeTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PetterChen on 2017/4/30.
 */

public class TradeTagUtils {
    private static String[] nameList = {"学习资料", "体育用品", "生活用品", "电子产品", "电脑相关"};
    private static int[] nameId = {R.drawable.sort_book, R.drawable.sort_esercise, R.drawable.sort_life,
            R.drawable.sort_phone, R.drawable.sort_computer};

    private static List<TradeTag> tradeTagList;

    public static List<TradeTag> getTradeTag() {
        tradeTagList = new ArrayList<>();
        for (int i = 0; i < nameList.length; i++) {
            TradeTag newTradeTag = new TradeTag();
            newTradeTag.setRId(nameId[i]);
            newTradeTag.setName(nameList[i]);
            tradeTagList.add(newTradeTag);
        }
        return tradeTagList;
    }
}
