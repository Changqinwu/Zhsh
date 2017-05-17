package com.qiming.wcq.mymapdemo.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.qiming.wcq.mymapdemo.R;

/**
 * Created by Administrator on 2017/2/13.
 */

public class ExitAppUtil {

    private final Context context;
    private String DefaultPwd = "dfqm";
    private long exitTime;

    public ExitAppUtil(Context context){
        this.context = context;
    }

    //退出app弹窗
    public void dialogExit() {
        final AlertDialog.Builder bulider = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.home_dialog_exit_layout,null);
        final EditText mEditPwd = (EditText) view.findViewById(R.id.edit_pwd);
        final EditText mEditConFirmPwd = (EditText) view.findViewById(R.id.edit_confirm_pwd);
        bulider.setView(view);
        bulider.setTitle("提示");
        bulider.setMessage("输入密码退出应用？");
        bulider.setIcon(R.mipmap.appicon);
        bulider.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String pwd = mEditPwd.getText().toString();
                final String confirmPwd = mEditConFirmPwd.getText().toString();
                if (!pwd.equals("")) {
                    if (pwd.equals(DefaultPwd)) {
                        System.exit(0);
                    } else {
                        ToastUtil.show(context, "密码输入有误。。。");
                    }
                } else {
                    ToastUtil.show(context, "密码不能为空。。。");
                }

            }
        });

        bulider.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        bulider.create().show();

    }

}
