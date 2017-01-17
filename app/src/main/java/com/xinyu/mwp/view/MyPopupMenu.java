package com.xinyu.mwp.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.xinyu.mwp.R;
import com.xinyu.mwp.entity.MyPopupMenuEntity;
import com.xinyu.mwp.helper.ShadowProperty;
import com.xinyu.mwp.helper.ShadowViewHelper;
import com.xinyu.mwp.listener.OnChildViewClickListener;
import com.xinyu.mwp.util.DisplayUtil;
import com.xinyu.mwp.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-03-14 11:31
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class MyPopupMenu extends PopupWindow {

    protected OnChildViewClickListener onChildViewClickListener;

    public void setOnChildViewClickListener(OnChildViewClickListener onChildViewClickListener) {
        this.onChildViewClickListener = onChildViewClickListener;
    }

    protected void onChildViewClick(View childView, int action, Object obj) {
        if (onChildViewClickListener != null)
            onChildViewClickListener.onChildViewClick(childView, action, obj);
    }

    protected void onChildViewClick(View childView, int action) {
        onChildViewClick(childView, action, null);
    }

    protected Context context;
    private List<MyPopupMenuEntity> lists;
    private LinearLayout rootLayout;


    public MyPopupMenu(Context context, List<MyPopupMenuEntity> lists) {
        this.context = context;
        this.lists = lists;
        initView();
        initListener();
        initData();
    }

    private void initListener() {

    }

    private void initData() {
        for (int i = 0; i < lists.size(); i++) {
            View itemView = View.inflate(context, R.layout.item_menu, null);
            rootLayout.addView(itemView);
            View view = rootLayout.getChildAt(i).findViewById(R.id.rootLayout);
            TextView textView = (TextView) rootLayout.getChildAt(i).findViewById(R.id.text);
            MyPopupMenuEntity entity = lists.get(i);
            String text = entity.getText();
            textView.setText(TextUtils.isEmpty(text) ? "" : text);
            view.setTag(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    onChildViewClick(v, (Integer) v.getTag());
                }
            });

        }
    }


    private void initView() {
        rootLayout = (LinearLayout) View.inflate(context, R.layout.ly_comm_menu, null);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setContentView(rootLayout);

        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        // popupWindow.setAnimationStyle(R.style.AnimBottom);
        int r = DisplayUtil.dip2px(5, context);
        ShadowViewHelper.bindShadowHelper(new ShadowProperty().setShadowColor(0x77000000).setShadowRadius(r), rootLayout, 0, 0);

    }

    public static List<MyPopupMenuEntity> toEntity(String[] texts) {
        if (texts == null || texts.length == 0) {
            return null;
        }
        List<MyPopupMenuEntity> list = new ArrayList<>();
        for (int i = 0; i < texts.length; i++) {
            MyPopupMenuEntity entity = new MyPopupMenuEntity();
            entity.setText(texts[i]);
            list.add(entity);
        }
        return list;
    }

    public void show(View view) {
        showAtLocation(view, Gravity.RIGHT | Gravity.TOP, 40, (int) view.getY() + 10 + Utils.getStatusHeight(context));
    }

    public void show1(View view) {
        view.measure(0, 0);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
//        int x = location[0];
        int y = location[1];
        showAtLocation(view, Gravity.RIGHT | Gravity.TOP, view.getWidth() + DisplayUtil.dip2px(16, context), y);
    }
}
