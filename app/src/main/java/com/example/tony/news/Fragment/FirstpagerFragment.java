package com.example.tony.news.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tony.news.MsgDataActivity;
import com.example.tony.news.R;
import com.example.tony.news.Utils.JsonUtils;
import com.example.tony.news.Utils.ProgressDialogUtils;
import com.example.tony.news.adapter.FirstPapgerAdapter;
import com.example.tony.news.bean.BannerBean;
import com.example.tony.news.bean.Data;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Tony on 16/11/18.
 */

//主界面的Fragment
public class FirstpagerFragment extends Fragment implements JsonUtils.CallBackListener, SwipeRefreshLayout.OnRefreshListener {

    ProgressDialogUtils utils;

    private List<Data> item_list;//记录当前真正要显示的数据

    private List<Data>[] msg_list;//保存了所有数据的集合数组

    List<String> banner_url;

    private int now_num = 7;//记录当前存放的数据条数

    private RecyclerView mRecyclerView;

    private FirstPapgerAdapter adapter;

    private int mPosition;//记录当前存在的页面

    private JsonUtils jsonUtils;//获取数据的工具类

    private BannerBean mBannerBean;//存储轮播图数据对象

    private SwipeRefreshLayout swipe;//下拉刷新空间

    private int lastVisibleItem;//记录最后一个位置的下标

    private final int LOADNUM = 4;//每次刷新加载的条数
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_layout,
                (ViewGroup) getActivity().findViewById(R.id.view_pager), false);

        getData();

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        swipe = (SwipeRefreshLayout) v.findViewById(R.id.swipe);

        swipe.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_orange_light);
        swipe.setOnRefreshListener(this);

        adapter = new FirstPapgerAdapter(getActivity(), item_list, mBannerBean);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        initListener();

        return v;
    }
    //给上拉实现事件监听
    private void initListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //状态发生变化时触发
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItem + 1 == adapter.getItemCount()) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            now_num += LOADNUM;
                            initData();
                        }
                    }, 1000);
                }
            }

            //滚动时监听
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = lm.findLastVisibleItemPosition();
            }
        });
        //item的点击事件
        adapter.setMyItemClickListener(new FirstPapgerAdapter.MyItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                String url = item_list.get(position - 1).getUrl();
                Intent intent = new Intent(getActivity(), MsgDataActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("position", mPosition);
                startActivity(intent);
            }
        });
    }

    //获取网络数据方法
    private void getData() {
        //轮播视图和item数据集合的初始化工作
        item_list = new ArrayList<>();
        mBannerBean = new BannerBean();

        //初始化弹窗utils
        utils = new ProgressDialogUtils();

        //注册本类去监听数据加载状态
        jsonUtils = new JsonUtils(this);

        //加载数据
        jsonUtils.getResult();

        //显示弹窗
        utils.showProgressDialog(getActivity(), "正在加载...");
    }

    //回调的更新界面方法
    @Override
    public void upData(List<Data>[] msg_list) {
        //关闭弹窗
        utils.closeProgressDialog();

        this.msg_list = msg_list;

        initData();//更新数据,重新分配数据,去填充界面
    }

    //分配数据，填充布局
    public void initData() {
        if (msg_list != null) {
            String[] img = new String[3];
            String[] title = new String[3];
            String[] toUrl = new String[3];

            item_list.clear();//清除缓存

            List<Data> data = msg_list[mPosition];
            for (int i = 0; i < 3; i++) {
                img[i] = data.get(i).getThumbnail();
                title[i] = data.get(i).getTitle();
                toUrl[i] = data.get(i).getUrl();
            }
            mBannerBean.setImg_url(img);
            mBannerBean.setTitle(title);
            mBannerBean.setToUrl(toUrl);

            for (int i = 3; i < now_num; i++) {
                item_list.add(data.get(i));
            }
            adapter.notifyDataSetChanged();
        }
    }

    //下拉刷新方法回调
    @Override
    public void onRefresh() {
        now_num += LOADNUM;//刷新操作执行后多显示4条数据
        initData();
        swipe.setRefreshing(false);
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    //滑动时更新数据
    public void setPosition(int position) {
        mPosition = position;
        initData();
    }
}
