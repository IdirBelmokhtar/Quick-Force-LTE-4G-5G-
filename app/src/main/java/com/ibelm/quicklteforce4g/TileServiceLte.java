package com.ibelm.quicklteforce4g;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import java.util.List;

public class TileServiceLte extends TileService {

    private final int STATE_ON = 1;
    private final int STATE_OFF = 0;
    private int toggle = STATE_ON;

    @Override
    public void onTileAdded() {
        //Toast.makeText(this, "onTileAdded", Toast.LENGTH_SHORT).show();
        super.onTileAdded();
    }

    @Override
    public void onStartListening() {

        //Toast.makeText(this, "onStartListening", Toast.LENGTH_SHORT).show();
        Tile tile = getQsTile();
        //tile.setIcon(Icon.createWithResource(getApplicationContext(), R.drawable._g_icon));
        tile.setLabel(getString(R.string.lte));
        tile.setState(Tile.STATE_ACTIVE);
        tile.updateTile();
        super.onStartListening();
    }

    @Override
    public void onClick() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Phone Info");
        builder.setMessage("Do you wont open phone info settings ?");
        builder.setPositiveButton("Open", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.ibelm.quicklteforce4g");
                    if (Build.VERSION.SDK_INT >= 30) {
                        SharedPreferences.Editor editor = getSharedPreferences("PHONE", MODE_PRIVATE).edit();
                        editor.putString("INFO", "true");
                        editor.apply();
                        launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivityAndCollapse(launchIntent);
                    } else {
                        Runtime.getRuntime().exec("am start --user 0 -n com.android.settings/.RadioInfo");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplication(), "Your device is not compatible with this App, Inconvenience is regretted :(", Toast.LENGTH_LONG).show();
                }
                dialogInterface.dismiss();
            }
        });

        showDialog(builder.create());

        /*Icon icon;
        if (toggle == STATE_ON) {
            toggle = STATE_OFF;
            icon = Icon.createWithResource(getApplicationContext(), R.drawable._g_icon);
            getQsTile().setState(Tile.STATE_INACTIVE);
        } else {
            toggle = STATE_ON;
            icon = Icon.createWithResource(getApplicationContext(), R.drawable._g_lte_icon);
            getQsTile().setState(Tile.STATE_ACTIVE);
        }
        getQsTile().setIcon(icon);*/
        getQsTile().updateTile();
        super.onClick();
    }

}
