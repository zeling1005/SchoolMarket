package com.peter.schoolmarket.mvp.more;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.adapter.recycler.RecyclerViewHolder;
import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Notice;
import com.peter.schoolmarket.mvp.more.notice.detail.NoticeDetailActivity;
import com.peter.schoolmarket.util.ResultInterceptor;
import com.peter.schoolmarket.util.TimeUtils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by PetterChen on 2017/5/5.
 */

public class MorePresenter implements IMorePresenter, IMoreListener {
    private IMoreView view;
    private Context context;
    private IMoreModel model;

    public MorePresenter(Context context,IMoreView view) {
        this.context = context;
        this.view = view;
        model = new MoreModel();
    }


    @Override
    public void refresh(Realm realm) {
        //本来就有刷新出现
        model.noticeDataReq(this, 0, realm);
    }

    @Override
    public void init(Realm realm) {
        RealmQuery<Notice> query =  realm.where(Notice.class);
        RealmResults<Notice> results = query.findAll();
        List<Notice> data =  realm.copyFromRealm(results);
        if (data.size()>0){
            initList(data);
        } else {
            //这里可以添加一个加载窗口
            view.showProgress();
            model.noticeDataReq(this, 0, realm);
        }
    }

    private void initList(final List<Notice> notices) {
        RecyclerCommonAdapter<?> adapter=new RecyclerCommonAdapter<Notice>(context,notices, R.layout.more_item) {
            @Override
            public void convert(RecyclerViewHolder holder, Notice item) {
                holder.setText(R.id.more_item_title,item.getTitle());
                holder.setText(R.id.more_item_name,item.getAuthorName());
                holder.setText(R.id.more_item_content,item.getContent());
                if (AppConf.useMock) {
                    holder.setText(R.id.more_item_time,"2017-04-12");
                } else {
                    String tem = TimeUtils.getDate(item.getCreateTime());
                    holder.setText(R.id.more_item_time,tem);
                }
            }
        };
        view.loadDataSuccess(adapter);
        adapter.setClickListener(new RecyclerCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, int position) {
                Notice item=notices.get(position);

                //跳转到notice详情页面
                //Toast.makeText(context, "jump to notice detail", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, NoticeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("notice", item);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onReqComplete(Result<List<Notice>> result, Realm realm) {
        view.hideRefresh();
        view.hideProgress();
        if (!ResultInterceptor.instance.resultDataHandler(result)){//判断是否Result数据为空
            return;
        }
        final List<Notice> noticeList = result.getData();
        initList(noticeList);
        final RealmResults<Notice> results = realm.where(Notice.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {//清空数据
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
        realm.executeTransactionAsync(new Realm.Transaction() {//重新加载数据
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(noticeList);
            }
        });
    }
}
