package com.yami.studio.banana.merchantapp.activity.disconnect;

import android.content.Context;
import android.content.Intent;

import static com.yami.studio.banana.merchantapp.activity.disconnect.DisconnectActivity.EXTRA_DISCONNECT;

public class DisconnectHandle {

    public static void onHandle(Context context, String msg){
        Intent i = new Intent(context, DisconnectActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(EXTRA_DISCONNECT, msg);
        context.startActivity(i);
    }

}
