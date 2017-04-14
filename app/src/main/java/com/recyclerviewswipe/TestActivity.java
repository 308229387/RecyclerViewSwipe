package com.recyclerviewswipe;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songyongmeng on 2017/4/13.
 */

public class TestActivity extends Activity {
    private Activity mContext;
    private List<ViewTypeBean> mViewTypeBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        mContext = this;
        mViewTypeBeanList = new ArrayList<>();
        for (int i = 0, j = 0; i < 30; i++, j++) {
            ViewTypeBean viewTypeBean = new ViewTypeBean();
            if (j == 0) {
                viewTypeBean.setViewType(MenuViewTypeAdapter.VIEW_TYPE_MENU_NONE);
                viewTypeBean.setContent("我没有菜单");
            } else if (j == 1) {
                viewTypeBean.setViewType(MenuViewTypeAdapter.VIEW_TYPE_MENU_SINGLE);
                viewTypeBean.setContent("我有1个菜单");
            } else if (j == 2) {
                viewTypeBean.setViewType(MenuViewTypeAdapter.VIEW_TYPE_MENU_MULTI);
                viewTypeBean.setContent("我有2个菜单");
            } else if (j == 3) {
                viewTypeBean.setViewType(MenuViewTypeAdapter.VIEW_TYPE_MENU_LEFT);
                viewTypeBean.setContent("我的左边有菜单，右边没有");
                j = -1;
            }
            mViewTypeBeanList.add(viewTypeBean);
        }

        SwipeMenuRecyclerView menuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuRecyclerView.addItemDecoration(new ListViewDecoration());

        menuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        menuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

        MenuViewTypeAdapter menuAdapter = new MenuViewTypeAdapter(mViewTypeBeanList);
        menuAdapter.setOnItemClickListener(onItemClickListener);

        menuRecyclerView.setAdapter(menuAdapter);
    }

    /**
     * 菜单创建器。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            if (viewType == MenuViewTypeAdapter.VIEW_TYPE_MENU_NONE) {// 根据Adapter的ViewType来决定菜单的样式、颜色等属性、或者是否添加菜单。
                // Do nothing.
            } else if (viewType == MenuViewTypeAdapter.VIEW_TYPE_MENU_SINGLE) {// 需要添加单个菜单的Item。
                SwipeMenuItem wechatItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_purple)
                        .setImage(R.mipmap.ic_action_wechat)
                        .setText("微信")
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(wechatItem);

            } else if (viewType == MenuViewTypeAdapter.VIEW_TYPE_MENU_MULTI) { // 是需要添加多个菜单的Item。
                SwipeMenuItem wechatItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_purple)
                        .setImage(R.mipmap.ic_action_wechat)
                        .setText("微信")
                        .setWidth(width)
                        .setHeight(height);

                swipeLeftMenu.addMenuItem(wechatItem);
                swipeRightMenu.addMenuItem(wechatItem);

                SwipeMenuItem addItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_green)
                        .setImage(R.mipmap.ic_action_add)
                        .setText("添加")
                        .setWidth(width)
                        .setHeight(height);

                swipeLeftMenu.addMenuItem(addItem);
                swipeRightMenu.addMenuItem(addItem);
            } else if (viewType == MenuViewTypeAdapter.VIEW_TYPE_MENU_LEFT) {
                SwipeMenuItem wechatItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_purple)
                        .setImage(R.mipmap.ic_action_wechat)
                        .setText("嘻嘻")
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(wechatItem);
            }
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(mContext, "我是第" + position + "条。", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
