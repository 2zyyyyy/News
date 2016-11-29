package com.example.tony.news.View;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.TextView;

import com.example.tony.news.R;

/*
 * Created by Tony on 16/11/18.
 */

/*
* 自定义加载弹窗样式
* */

public class CommonProgressDialog extends Dialog {
    public CommonProgressDialog(Context context) {
        super(context, R.style.commonProgressDialog);
        setContentView(R.layout.commonprogressdialog);
        //显示在屏幕中间
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }
    //设置加载消息的方法
    public void setMessage(String s){
        TextView textView = (TextView) this.findViewById(R.id.loadTextView);
        textView.setText(s);
    }

}
