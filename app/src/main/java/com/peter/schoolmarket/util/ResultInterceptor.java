package com.peter.schoolmarket.util;

import com.peter.schoolmarket.data.dto.Result;

/**
 * Created by PetterChen on 2017/4/28.
 */

public enum ResultInterceptor {
    instance;

    public boolean resultHandler(final Result<?> result){
        if (result==null){
            return false;
        }

        switch (result.getCode()) {
            case 99:
                return false;
            case 100:
                return true;
            default:
                return false;
        }
    }

    public boolean resultDataHandler(final Result<?> result){

        if (result==null||result.getData()==null){
            return false;
        }
        switch (result.getCode()) {
            case 100:
                return true;
            case 99:
                return false;
            default:
                return false;
        }
    }
}
