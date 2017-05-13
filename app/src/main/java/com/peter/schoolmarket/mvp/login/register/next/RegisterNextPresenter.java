package com.peter.schoolmarket.mvp.login.register.next;

import android.content.Context;
import android.widget.Toast;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;

/**
 * Created by PetterChen on 2017/4/19.
 */

public class RegisterNextPresenter implements IRegisterNextPresenter, OnRegisterNextListener {

    private IRegisterNextModel iRegisterNextModel;
    private Context context;
    private IRegisterNextView iRegisterNextView;

    public RegisterNextPresenter(Context context, IRegisterNextView iRegisterNextView) {
        iRegisterNextModel = new RegisterNextModel();
        this.context = context;
        this.iRegisterNextView = iRegisterNextView;
    }

    @Override
    public void addPhoneNum(String phoneNumber, String username, String password) {
        iRegisterNextView.showLoading();
        iRegisterNextModel.addPhoneNumber(phoneNumber, username, password, this);
    }

    @Override
    public void registerNextResult(Result<User> result) {
        iRegisterNextView.hideLoading();
        switch (result.getCode()) {
            case 99://网络异常或者系统错误
                iRegisterNextView.addFailed();
                break;
            case 100://操作成功
                LoginInfoExecutor.logIn(context, result.getData());
                iRegisterNextView.addSuccess();
                break;
            default:
                break;
        }
    }
}
