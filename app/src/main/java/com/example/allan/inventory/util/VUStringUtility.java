package com.example.allan.inventory.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.allan.inventory.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


public class VUStringUtility {


    public static boolean stringNotNull(String str) {
        return str != null && !str.equals("null");
    }

    public static boolean stringNotEmpty(String str) {
        return !str.isEmpty();
    }

    public static String[] splitByNumber(String s, int chunkSize) {
        int chunkCount = (s.length() / chunkSize) + (s.length() % chunkSize == 0 ? 0 : 1);
        String[] returnVal = new String[chunkCount];
        for (int i = 0; i < chunkCount; i++) {
            returnVal[i] = s.substring(i * chunkSize, Math.min((i + 1) * chunkSize, s.length()));
        }
        return returnVal;
    }


    public static boolean validateEditText(EditText editText) {
        return stringNotEmpty(editText.getText().toString().trim());
    }

    public static boolean validateText(TextView textView) {
        return stringNotEmpty(textView.getText().toString().trim());
    }

  /*  public static void exitApp(final Activity activity) {
        new AlertDialog.Builder(activity).setTitle(activity.getResources().getString(R.string.exit_text))
                .setMessage(activity.getResources().getString(R.string.close_app_text))
                .setPositiveButton(activity.getResources().getString(R.string.yes_hint), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                }).setNegativeButton(activity.getResources().getString(R.string.no_hint), null).show();
    }



        alertDialogLIve = alertDialogBuilder.create();
        alertDialogLIve.show();
    }
*/
//getResources().getString(R.string.no_hint)

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentDate() {

        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);

        return sdf.format(c.getTime());
    }

    public static int differenceInSecond(String eventStartDate) {

        Calendar c = Calendar.getInstance();
//      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);

        String currentDate = sdf.format(c.getTime());

        long diffInSecond = 0;

        try {
            Date date1 = sdf.parse(eventStartDate);
            Date date2 = sdf.parse(currentDate);

            long diffInMillisec = date2.getTime() - date1.getTime();
//          long diffInMillisec = date1.getTime() - date2.getTime();
            diffInSecond = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);

            Log.e(" diffInSecond ", "" + diffInSecond + "");

        } catch (Exception e) {
            e.printStackTrace();
        }

        String s = String.valueOf(diffInSecond);

        int seconds = Integer.valueOf(s);

        return seconds;
    }


}
