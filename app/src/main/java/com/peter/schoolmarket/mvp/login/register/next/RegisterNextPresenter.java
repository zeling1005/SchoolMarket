package com.peter.schoolmarket.mvp.login.register.next;

import android.content.Context;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.User;

/**
 * Created by PetterChen on 2017/4/19.
 */

public class RegisterNextPresenter implements IRegisterNextPresenter, OnRegisterNextListener {

    private IRegisterNextModel iRegisterNextModel;
    private Context context;
    private IRegisterNextView iRegisterNextView;

    private RegisterNextPresenter() {

    }

    public RegisterNextPresenter(Context context, IRegisterNextView iRegisterNextView) {
        iRegisterNextModel = new RegisterNextModel();
        this.context = context;
        this.iRegisterNextView = iRegisterNextView;
    }

    @Override
    public void addPhoneNum(String phoneNumber) {
        iRegisterNextView.showLoading();
        iRegisterNextModel.addPhoneNumber(phoneNumber, this);
    }

    @Override
    public void registerNextResult(Result<User> result) {
        iRegisterNextView.hideLoading();
        /*if (result==null){
            return;
        }*/
        switch (result.getCode()) {
            case 100:
                //SharedPreferencesStorage.instance.saveUser(context,result.getData());
                iRegisterNextView.addSuccess();
                break;
            case 99:
                iRegisterNextView.addFailed();
                break;
            default:
                break;
        }
    }
}
