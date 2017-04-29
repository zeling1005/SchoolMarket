package com.peter.schoolmarket.util;

import com.peter.schoolmarket.data.dto.Result;

/**
 * Created by PetterChen on 2017/4/28.
 */

public enum ResultInterceptor {
    instance, ResultInterceptor;

    public boolean resultHandler(final Result<?> result){
        if (result==null){
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

    public boolean resultHandler(final Result<?> result,int...jump){//jump是个数不定的数组

        if (result==null){
            return false;
        }

        for (int i:jump){
            if (result.getCode()==i){
                return true;
            }
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
    public boolean resultDataHandler(final Result<?> result,int...jump){

        if (result==null||result.getData()==null){
            return false;
        }

        for (int i:jump){
            if (result.getCode()==i){
                return true;
            }
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
