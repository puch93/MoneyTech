package kr.co.core.money_tech.activity;

import android.content.Intent;
import android.os.Bundle;

public class PushAct extends BaseAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        finishAffinity();
        startActivity(new Intent(this, SplashAct.class));
        finish();
    }
}
