package com.peter.schoolmarket.adapter.recycler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by PetterChen on 2017/4/28.
 * 例子：http://blog.csdn.net/u014315849/article/details/52537700
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public RecyclerViewHolder(Context context, View itemView, ViewGroup parent)
    {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }


    public static RecyclerViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId)
    {

        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        return new RecyclerViewHolder(context, itemView, parent);
    }


    /**
     * 通过viewId获取控件
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    //text源，例子：holder.setText(R.id.text_view, "text");
    public RecyclerViewHolder setText(int viewId, String text)
    {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    //图片源
    public RecyclerViewHolder setImageResource(int viewId, int resId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    //点击监听事件
    public RecyclerViewHolder setOnClickListener(int viewId,
                                                 View.OnClickListener listener)
    {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    //头像源
    public RecyclerViewHolder setFrescoImg(int viewId, Uri uri){
        SimpleDraweeView view=getView(viewId);
        view.setImageURI(uri);
        return this;
    }

    //分割线背景颜色
    public RecyclerViewHolder setLineBgColor(int viewId,int resId){
        LinearLayout layout=getView(viewId);
        layout.setBackgroundColor(resId);
        return this;
    }

    /*public RecyclerViewHolder setSendMsgListener(int viewId, final String phoneNumber,final String username){
        LinearLayout layout=getView(viewId);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri smsToUri = Uri.parse("smsto:"+phoneNumber);
                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                intent.putExtra("sms_body","你好"+username+",");
                mContext.startActivity(intent);
            }
        });
        return this;
    }*/

    /*public RecyclerViewHolder setConfirmDialogListener(int viewId, MessageView view, final Msg item){
        LinearLayout layout=getView(viewId);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getKind()==1){
                    view.whenShowConfirmDialog(item);
                }
            }
        });
        return this;
    }*/
}
