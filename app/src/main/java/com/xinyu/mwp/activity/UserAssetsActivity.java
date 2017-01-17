package com.xinyu.mwp.activity;

import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseRefreshAbsListControllerActivity;
import com.xinyu.mwp.adapter.UserAssetsAdapter;
import com.xinyu.mwp.adapter.base.IListAdapter;
import com.xinyu.mwp.entity.UserAssetsEntity;
import com.xinyu.mwp.entity.UserAssetsItemEntity;
import com.xinyu.mwp.listener.OnRefreshListener;
import com.xinyu.mwp.util.DisplayUtil;
import com.xinyu.mwp.util.TestDataUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/11.
 */

public class UserAssetsActivity extends BaseRefreshAbsListControllerActivity<UserAssetsItemEntity> {

    @ViewInject(R.id.moneyLayout)
    private View moneyLayout;
    @ViewInject(R.id.money)
    private TextView money;
    @ViewInject(R.id.bottomLayout)
    private View bottomLayout;
    @ViewInject(R.id.contentViewLayout)
    private View contentViewLayout;

    private UserAssetsAdapter adapter;
    private UserAssetsEntity entity;

    @Override
    protected IListAdapter<UserAssetsItemEntity> createAdapter() {
        return adapter = new UserAssetsAdapter(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_userassets;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("我的资产");
        toggleContentViewLayoutMarginTop(45);
    }

    @Event(value = {R.id.cash, R.id.recharge})
    private void click(View view) {
        switch (view.getId()) {
            case R.id.cash:
                next(CashActivity.class);
                break;
            case R.id.recharge:
                next(RechargeActivity.class);
                break;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });
    }

    private void doRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                entity = TestDataUtil.getUserAssetsEntity();
                toggleContentViewLayoutMarginTop(0);
                money.setText(entity.getMoney());
                moneyLayout.setVisibility(View.VISIBLE);
                getRefreshController().refreshComplete(entity.getAssets());
                bottomLayout.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }

    private void toggleContentViewLayoutMarginTop(int topDp) {
        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) contentViewLayout.getLayoutParams();
        rl.topMargin = DisplayUtil.dip2px(topDp, context);
        contentViewLayout.setLayoutParams(rl);
    }
}
