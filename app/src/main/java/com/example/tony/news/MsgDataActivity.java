package com.example.tony.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MsgDataActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_detail);

        webView = (WebView) findViewById(R.id.webView);

        //打开页面时自适应屏幕
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);//可根据任意比例缩放

        int position = getIntent().getIntExtra("position", 0);//获取传过来的position
        String url = getUrlByPosition(position);//获取传过来的url

        webView.loadUrl(url);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_msg);

        setSupportActionBar(toolbar);

        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //设置返回按钮事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String getUrlByPosition(int position) {
        String oldUrl = getIntent().getStringExtra("url");
        StringBuilder url = new StringBuilder();
        //获取网页形式路径中间数字
        String num = getMainNumber(oldUrl);
        switch (position) {
            case 0://要闻-手机版格式-->http://inews.ifeng.com/50252560/news.shtml
                url = url.append("http://inews.ifeng.com/").append(num).append("/news.shtml");
                break;
            case 1://财经-手机版格式-->http://ifinance.ifeng.com/?srctag=xzydh4
                url = url.append("http://ifinance.ifeng.com/").append(num).append("/news.shtml");
                break;
            case 2://体育-手机版格式-->http://isports.ifeng.com/?srctag=xzydh3
                url = url.append("http://isports.ifeng.com/").append(num).append("/news.shtml");
                break;
            case 3://军事-手机版格式-->http://imil.ifeng.com/?srctag=xzydh6
                url = url.append("http://imil.ifeng.com/").append(num).append("/news.shtml");
                break;
            case 4://科技-手机版格式-->http://itech.ifeng.com/?srctag=xzydh10
                url = url.append("http://itech.ifeng.com/").append(num).append("/news.shtml");
                break;
            case 5://历史-手机版格式-->http://ihistory.ifeng.com/?srctag=xzydh12
                url = url.append("http://ihistory.ifeng.com/").append(num).append("/news.shtml");
                break;
            case 6://凤凰号-手机版格式-->http://smart.ifeng.com/#fenghuanghao?srctag=xzydh16
                url = url.append("http://smart.ifeng.com/Auto_2087_").append(num).append("/news.shtml?chnn=smart_fhh");
                break;
        }
        return url.toString();
    }
    //截取中间数字的方法
    private String getMainNumber(String oldUrl) {
        oldUrl = oldUrl.substring(oldUrl.lastIndexOf("/") + 1, oldUrl.lastIndexOf("."));
        if (oldUrl.contains("_")) {
            oldUrl = oldUrl.substring(0, oldUrl.lastIndexOf("_"));
        }
        return oldUrl;
    }
}
