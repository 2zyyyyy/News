package com.example.tony.news.Utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.tony.news.bean.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
 * Created by Tony on 2016/11/20.
 */
//加载数据模块
public class JsonUtils {

    private CallBackListener listen;

    public JsonUtils() {

    }
    //通知谁更新，得告诉我（外部关注加载数据类通过此方法注册）
    public JsonUtils(CallBackListener listen) {
        this.listen = listen;
    }

    private List<Data>[] msg_list;

    //明确访问的路径 http://www.news.ifeng.com/ 获取网络数据
    //返回的数据格式:[[{}],[{},{},{}],[{},{}]]

    public void getResult() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //1、需要创建一个OkHttpClient对象，一个APP最好只实例化一个对象
                OkHttpClient client = new OkHttpClient();
                //2、新建一个请求
                Request request = new Request.Builder().url("http://news.ifeng.com/").build();
                //3、执行请求，获取相应数据
                Call call = client.newCall(request);
                // 4、加入调度，执行回调处理
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String str = response.body().string();
                            Message msg = new Message();
                            msg.obj = str;
                            msg.arg1 = 0x1;
                            mHandler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 0x1) {
                //处理返回的字符串
                getJson(msg.obj.toString());
            }
        }
    };
    //截取返回数据中的Json格式数据
    private void getJson(String msg) {
        String json = null;
        if (msg != null) {
            //indexOf不包含前面的字符，所以后面的字符要加上3个长度
            json = msg.substring(msg.indexOf("[[{"), msg.indexOf("}]]") + 3);
            Log.i("", "getJson: ---->" + json);
        }
        initMessageList(json);
    }
    //拿到JSON字符串得到有效数据
    public void initMessageList(String json) {
        try {
            JSONArray array = new JSONArray(json);
            msg_list = new ArrayList[array.length()];

            for (int i = 0; i < array.length(); i++) {
                JSONArray arr = array.getJSONArray(i);
                msg_list[i] = new ArrayList<>();
                for (int j = 0; j < arr.length(); j++) {
                    JSONObject obj = arr.getJSONObject(j);
                    Data data = new Data();
                    data.setThumbnail(obj.getString("thumbnail"));
                    data.setTitle(obj.getString("title"));
                    data.setUrl(obj.getString("url"));
                    msg_list[i].add(data);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //通知注册进来的类去更新界面
        listen.upData(msg_list);
    }
    //关注数据加载类的父类借口
    public interface CallBackListener {
        void upData(List<Data>[] msg_list);
    }
}
