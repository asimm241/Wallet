package com.wallet.app.Utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.wallet.app.R;

/**
 * Created by Abdul Haseeb on 4/29/2018.
 */

public class ViewsUtilities {

    public static void showAlertDialog(final Context context,String title, String message, final View.OnClickListener onClickListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogTheme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onClickListener.onClick(null);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }



}
