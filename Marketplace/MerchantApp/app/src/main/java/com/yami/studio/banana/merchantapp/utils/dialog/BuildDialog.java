package com.yami.studio.banana.merchantapp.utils.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class BuildDialog {

    public static void create(Context context, String msg, final ActionDialog actionDialog){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actionDialog.positiveButton();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        dialog.show();
    }

}
