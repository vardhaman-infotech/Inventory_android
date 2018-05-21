package com.example.allan.inventory;


import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.allan.inventory.util.Permissions;
import com.example.allan.inventory.util.VUStringUtility;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class inventory_fragment extends Fragment {
    //    private TrackGPS gps;
    double longitude;
    double latitude;

    private Button btnAdditem;
    private Button btnShowitems;
    private Spinner spQuality;
    private EditText invoice;

    public inventory_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.inventory_fragment, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        final String[] qualityoptions;
        btnAdditem = (Button) getActivity().findViewById(R.id.btnAdditem);
        btnShowitems = (Button) getActivity().findViewById(R.id.btnShowitems);
        spQuality = (Spinner) getActivity().findViewById(R.id.quality);
        invoice = (EditText) getActivity().findViewById(R.id.invoice);

        qualityoptions =
                getResources().getStringArray(R.array.quality);
        spQuality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int index = arg0.getSelectedItemPosition();
                MainActivity.Quality = qualityoptions[index];
                Toast.makeText(getActivity(),
                        "You have selected item : " + qualityoptions[index],
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        btnAdditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                gps = new TrackGPS(getActivity());
                String invoiceNumber = invoice.getText().toString().trim();

                if (!invoiceNumber.isEmpty()) {
                    addItem();
                } else {
                    Toast.makeText(getActivity(), "Please enter Invoice Number", Toast.LENGTH_LONG).show();
                }

//                String timestr = null;
//                if (ContextCompat.checkSelfPermission(getActivity(),
//                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(getActivity(),
//                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
//                }
//
//                InventoryLog logobj = new InventoryLog(MainActivity.Username, currentDate(),
//                        invoice.getText().toString(), MainActivity.Quality, );
//                MainActivity.entries.add(logobj);
//                invoice.setText("");
//                spQuality.setSelected(false);
            }
        });

        btnShowitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemFragment frag = new ItemFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.pagePlace, frag);
                ft.commit();
            }


        });
    }

    private void addItem() {

        if (Permissions.isGPSPermissionGranted(getActivity())) {
            TrackGPS gpsTracker = new TrackGPS(getActivity());
            if (gpsTracker.canGetLocation()) {

                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                Log.e("latitude ", latitude + "");
                Log.e("longitude ", longitude + "");

                if (longitude == 0.0) {

                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    if (locationManager != null) {
                        Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                        Log.e("currentLatitude ", "aaa  " + myLocation.getLatitude() + "  aaa");
                        Log.e("currentLongitude ", "aaa  " + myLocation.getLongitude() + "  aaa");

                        latitude = myLocation.getLatitude();
                        longitude = myLocation.getLongitude();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.try_Again), Toast.LENGTH_LONG).show();
                    }
                }

                if (longitude != 0.0) {

                    Log.e("Username ", " " + MainActivity.Username + " ");
                    Log.e("CurrentDate ", " " + VUStringUtility.getCurrentDate() + " ");
                    Log.e("invoice ", " " + invoice.getText().toString().trim() + " ");
                    Log.e("Quality ", " " + MainActivity.Quality + " ");
                    Log.e("latitude ", " " + String.valueOf(latitude) + " ");
                    Log.e("longitude ", " " + String.valueOf(longitude) + " ");

                    InventoryLog logObj = new InventoryLog(MainActivity.Username, VUStringUtility.getCurrentDate(),
                            invoice.getText().toString(), MainActivity.Quality, String.valueOf(latitude),
                            String.valueOf(longitude));

                    MainActivity.entries.add(logObj);
                    invoice.setText("");
                    spQuality.setSelected(false);

                } else {
                    Toast.makeText(getActivity(), getString(R.string.try_Again), Toast.LENGTH_LONG).show();
                }
//                checkIn(String.valueOf(latitude), String.valueOf(longitude));

            } else {
                gpsTracker.showSettingsAlert();
            }

        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.location_permission_denied_str), Toast.LENGTH_SHORT).show();
        }
    }

    private String currentDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH) + 1;
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);

        String currenttime = day + ":" + month + ":" + year + " " + hour + ":" + minute + ":" + seconds;
        return currenttime;
    }
}
