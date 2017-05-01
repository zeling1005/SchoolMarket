package com.peter.schoolmarket.mvp.login.register.next;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.di.components.DaggerRegisterNextComponent;
import com.peter.schoolmarket.di.modules.RegisterNextModule;
import com.peter.schoolmarket.mvp.base.BaseActivity;
import com.peter.schoolmarket.mvp.login.LoginActivity;
import com.peter.schoolmarket.mvp.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by PetterChen on 2017/4/19.
 */

public class RegisterNextActivity extends BaseActivity implements IRegisterNextView {

    @Inject
    private IRegisterNextPresenter iRegisterNextPresenter;

    @Inject
    private MaterialDialog registerNextProgress;

    private TextInputLayout phoneLayout;
    //EditText phoneText;
    private TextView registerNextSub;
    private TextView getCode;
    private TextInputLayout code;
    private String oldCode;
    private String userId;
    private String phone;
    private static int timeCount = 60;
    private Handler timerClock;


    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.register_next_activity);
        initValiable();
    }

    private void initValiable() {
        DaggerRegisterNextComponent
                .builder()
                .registerNextModule(new RegisterNextModule(this, this))
                .build()
                .inject(this);
        phoneLayout = (TextInputLayout) findViewById(R.id.register_next_phone);
        //phoneText = (EditText) findViewById(R.id.register_next_phone_text);
        registerNextSub = (TextView) findViewById(R.id.register_next_sub);
        getCode = (TextView) findViewById(R.id.register_next_get_code);
        code = (TextInputLayout) findViewById(R.id.register_next_security_code);
        Intent intent=getIntent();
        userId=intent.getStringExtra("userId");
        registerNextSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = phoneLayout.getEditText().getText().toString().trim();
                if (phone.isEmpty() || phone.length() != 11){
                    phoneLayout.setErrorEnabled(true);
                    phoneLayout.setError("请输入正确的手机号");
                }else {
                    phoneLayout.setErrorEnabled(false);
                    String newCode = code.getEditText().getText().toString().trim();
                    if (!(newCode.isEmpty()) && newCode.equals(oldCode)) {
                        code.setErrorEnabled(false);
                        iRegisterNextPresenter.addPhoneNum(phone, userId);
                    } else {
                        code.setErrorEnabled(true);
                        code.setError("请输入正确的验证码");
                    }
                }
            }
        });
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS(phone);
            }
        });


    }

    private void sendSMS(String phoneNumber){
        oldCode = "";
        // TODO Auto-generated method stub
        for(int i=0;i<6;i++){//产生一个六位数的激活码
            int k=(int) (Math.random()*10);
            oldCode+=k;
        }
        String message = "[闲货]短信验证码 " + oldCode + ",请在十分钟内完成验证。";
        //处理返回的发送状态
        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(RegisterNextActivity.this, 0, sentIntent,
                0);
        // register the Broadcast Receivers
        (RegisterNextActivity.this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Log.i("====>", "Activity.RESULT_OK");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Log.i("====>", "RESULT_ERROR_GENERIC_FAILURE");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Log.i("====>", "RESULT_ERROR_NO_SERVICE");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Log.i("====>", "RESULT_ERROR_NULL_PDU");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Log.i("====>", "RESULT_ERROR_RADIO_OFF");
                        break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION));

        //获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, sentPI, null);
        Toast.makeText(RegisterNextActivity.this,
                "短信发送成功", Toast.LENGTH_SHORT)
                .show();

        getCode.setClickable(false);
        timerClock.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (timeCount > 0){
                    getCode.setText("" + --timeCount + "秒后重新发送");
                    timerClock.postDelayed(this, 1000);
                } else {
                    getCode.setText((RegisterNextActivity.this).getResources().getString(R.string.register_next_get_code));
                    getCode.setClickable(true);
                }
            }
        }, 1000);

        //拆分短信内容（手机短信长度限制）
        /*List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, sentPI, null);
        }*/
    }

    @Override
    public String getPhoneNumber() {
        if (phone.isEmpty()){
            return null;
        }
        return phone;
    }

    @Override
    public void showLoading() {
        registerNextProgress.show();
    }

    @Override
    public void hideLoading() {
        registerNextProgress.dismiss();
    }

    @Override
    public void addSuccess() {
        Intent intent = new Intent(RegisterNextActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void addFailed() {
        new SuperToast(RegisterNextActivity.this)
                .setText("网络异常或者系统错误")
                .setDuration(Style.DURATION_LONG)
                .setColor(PaletteUtils.getTransparentColor(PaletteUtils.MATERIAL_RED))
                .setAnimations(Style.ANIMATIONS_POP)
                .show();
    }
}
