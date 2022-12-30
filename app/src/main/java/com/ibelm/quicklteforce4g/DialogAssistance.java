package com.ibelm.quicklteforce4g;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogAssistance extends Dialog {
    //field
    private LinearLayout done;
    private TextView assistanceYTB;

    //constructor
    public DialogAssistance(Activity activity){
        super(activity , androidx.appcompat.R.style.ThemeOverlay_AppCompat_Dialog);
        setContentView(R.layout.dialog_assistance);

        this.done = findViewById(R.id.done);
        this.assistanceYTB = findViewById(R.id.assistanceYTB);
        assistanceYTB.setPaintFlags(assistanceYTB.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        assistanceYTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(activity.getString(R.string.ytb))));
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
