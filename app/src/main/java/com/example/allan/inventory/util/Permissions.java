package com.example.allan.inventory.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

/***
 * Created by android2 on 21/05/18.
 */

public class Permissions {

    public static boolean isAppCachePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(Manifest.permission.CLEAR_APP_CACHE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission","Permission is granted");
                return true;
            } else {

                Log.e("Permission","Permission is revoked");
                activity.requestPermissions( new String[]{Manifest.permission.CLEAR_APP_CACHE}, 120);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.e("Permission","Permission is granted");
            return true;
        }
    }

    public static boolean isContactPermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission","Permission is granted");
                return true;
            } else {

                Log.e("Permission","Permission is revoked");
                activity.requestPermissions( new String[]{Manifest.permission.READ_CONTACTS}, 121);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.e("Permission","Permission is granted");
            return true;
        }
    }

    public static boolean isGPSPermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED && activity.checkSelfPermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission", "Permission is granted");
                return true;
            } else {

                Log.e("Permission", "Permission is revoked");
                activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 122);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.e("Permission", "Permission is granted");
            return true;
        }
    }

    public static boolean isStoragePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && activity.checkSelfPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission","Permission is granted");
                return true;
            } else {

                Log.e("Permission","Permission is revoked");
                activity.requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.e("Permission","Permission is granted");
            return true;
        }
    }

 public static boolean isStorageReadPermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission","Permission is granted");
                return true;
            } else {

                Log.e("Permission","Permission is revoked");
                activity.requestPermissions( new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.e("Permission","Permission is granted");
            return true;
        }
    }


  public static boolean isStorageCameraPermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && activity.checkSelfPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && activity.checkSelfPermission(
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission","Permission is granted");
                return true;
            } else {

                Log.e("Permission","Permission is revoked");
                activity.requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1234);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.e("Permission","Permission is granted");
            return true;
        }
    }


    public static boolean isCameraPermissionGranted(Activity activity) {

        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission", "Permission is granted");
                return true;
            } else {

                Log.e("Permission", "Permission is revoked");
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, 124);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.e("Permission", "Permission is granted");
            return true;
        }
    }

    public static boolean isPermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (
                    (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                    (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                    (activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                    (activity.checkSelfPermission(android.Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED)) {
                Log.e("Permission", "Permission is granted");
                return true;
            } else {

                Log.e("Permission", "Permission is revoked");
                activity.requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE}, 1234);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.e("Permission", "Permission is granted");
            return true;
        }
    }


}
