package kr.co.core.money_tech.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;

import kr.co.core.money_tech.R;
import kr.co.core.money_tech.activity.BaseAct;
import kr.co.core.money_tech.databinding.DialogGalleryBinding;


public class GalleryDlg extends BaseAct {
    DialogGalleryBinding binding;
    Activity act;

    public static final String CAMERA = "camera";
    public static final String ALBUM = "gallery";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.dialog_gallery, null);
        act = this;

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

        setFinishOnTouchOutside(false);

        binding.btnGallery.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("result", ALBUM);
            setResult(RESULT_OK, intent);
            finish();
        });

        binding.btnCamera.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("result", CAMERA);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}