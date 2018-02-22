package io.githubleonard04.ignasiusleo.application.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

/**
 * Created by ignasiusleo on 04/10/17.
 */

public class CustomDialogClass extends Dialog implements
    android.view.View.OnClickListener {

    public Activity activity;
    public ImageButton yes, no;

    public CustomDialogClass(Activity a) {
        super(a);
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(io.githubleonard04.ignasiusleo.application.R.layout.custom_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        yes = findViewById(io.githubleonard04.ignasiusleo.application.R.id.btn_yes);
        no = findViewById(io.githubleonard04.ignasiusleo.application.R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case io.githubleonard04.ignasiusleo.application.R.id.btn_yes:
                activity.finish();
                break;
            case io.githubleonard04.ignasiusleo.application.R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
