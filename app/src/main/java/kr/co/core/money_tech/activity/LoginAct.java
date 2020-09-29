package kr.co.core.money_tech.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.core.money_tech.R;
import kr.co.core.money_tech.databinding.ActivityLoginBinding;
import kr.co.core.money_tech.server.ReqBasic;
import kr.co.core.money_tech.server.netUtil.HttpResult;
import kr.co.core.money_tech.server.netUtil.NetUrls;
import kr.co.core.money_tech.util.AppPreference;
import kr.co.core.money_tech.util.Common;
import kr.co.core.money_tech.util.StringUtil;

public class LoginAct extends BaseAct implements View.OnClickListener {
    ActivityLoginBinding binding;
    public static Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        binding = DataBindingUtil.setContentView(this, R.layout.activity_login, null);
        act = this;

        /* top margin */
        binding.area.setPadding(0, statusBarHeight(), 0, 0);

        binding.btnLogin.setOnClickListener(this);
        binding.btnJoin.setOnClickListener(this);
        binding.btnFindAccount.setOnClickListener(this);
    }

    private int statusBarHeight() {
        int res_id = getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (res_id > 0) {
            return getResources().getDimensionPixelSize(res_id);
        } else {
            return 0;
        }

    }

    private void doLogin() {
        ReqBasic server = new ReqBasic(act, NetUrls.DOMAIN) {
            @Override
            public void onAfter(int resultCode, HttpResult resultData) {
                if (resultData.getResult() != null) {
                    try {
                        JSONObject jo = new JSONObject(resultData.getResult());

                        if( StringUtil.getStr(jo, "result").equalsIgnoreCase("Y")) {
                            JSONObject job = jo.getJSONObject("data");

                            AppPreference.setPrefString(act, AppPreference.PREF_MIDX, StringUtil.getStr(job, "m_idx"));
                            AppPreference.setPrefString(act, AppPreference.PREF_ID, binding.id.getText().toString());
                            AppPreference.setPrefString(act, AppPreference.PREF_PW, binding.pw.getText().toString());
                            AppPreference.setPrefString(act, AppPreference.PREF_NICK, StringUtil.getStr(job, "m_nick"));
                            AppPreference.setPrefBoolean(act, AppPreference.AUTO_LOGIN, binding.ckKeep.isChecked());
                            AppPreference.setPrefBoolean(act, AppPreference.LOGIN_STATE, true);

                            Intent intent = new Intent(act, MainAct.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else {
                            Common.showToast(act, StringUtil.getStr(jo, "message"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Common.showToastNetwork(act);
                    }
                } else {
                    Common.showToastNetwork(act);
                }
            }
        };

        server.setTag("Login");
        server.addParams("dbControl", NetUrls.LOGIN);
        server.addParams("m_id", binding.id.getText().toString());
        server.addParams("m_pass", binding.pw.getText().toString());
        server.addParams("fcm", AppPreference.getPrefString(act, AppPreference.PREF_FCM));
        server.addParams("m_uniq", Common.getDeviceId(act));
        server.execute(true, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (binding.id.length() == 0) {
                    Common.showToast(act, "아이디를 입력해주세요");
                } else if (binding.pw.length() == 0) {
                    Common.showToast(act, "비밀번호를 입력해주세요");
                } else {
                    doLogin();
                }
                break;

            case R.id.btn_join:
                startActivity(new Intent(act, JoinAct.class));
                break;

            case R.id.btn_find_account:
                startActivity(new Intent(act, FindAct.class));
                break;
        }
    }
}