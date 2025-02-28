package kr.co.core.money_tech.server;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AppCompatDialog;

import kr.co.core.money_tech.R;


public class CustomLoading {

    AppCompatDialog progressDialog;
    Context act;

    public CustomLoading(Context act) {
        this.act = act;
    }

    public void progressON(String msg) {
        if (act == null || ((Activity) act).isFinishing()) {
            return;
        }

        if (!(progressDialog != null && progressDialog.isShowing())) {
            progressDialog = new AppCompatDialog(act);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.dialog_loading);
            progressDialog.show();
        }

//        /* set animation */
//        ImageView aniImg = (ImageView) progressDialog.findViewById(R.id.iv_loading);
//        aniImg.setAnimation(AnimationUtils.loadAnimation(act, R.anim.blink_anim));
//
//        /* set text */
//        TextView message = (TextView)progressDialog.findViewById(R.id.tv_loading);
//        if (!StringUtil.isNull(msg)){
//            message.setText(msg);
//        }
    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
