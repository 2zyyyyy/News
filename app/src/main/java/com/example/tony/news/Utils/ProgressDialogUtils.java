package com.example.tony.news.Utils;

/*
 * Created by Tony on 16/11/18.
 */
import android.app.Activity;
import com.example.tony.news.View.CommonProgressDialog;

/*
* 弹窗工具类
*/
public class ProgressDialogUtils {

    private CommonProgressDialog dialog;
    private Activity activity;
    //显示方法
    public void showProgressDialog(Activity activity, String msg) {
        this.activity = activity;
        if (dialog == null) {
            dialog = new CommonProgressDialog(activity);
        }
            if (msg == null) {
                msg = "正在加载...";
            }

        if (!activity.isFinishing() && !dialog.isShowing()) {
            dialog.show();
        }
        dialog.setMessage(msg);
    }

    //关闭方法
    public void closeProgressDialog() {
        if (dialog != null && !activity.isFinishing()) {
            dialog.dismiss();//如果dialog显示，且activity没挂，则关闭dialog
            dialog = null;
        }
    }
}
