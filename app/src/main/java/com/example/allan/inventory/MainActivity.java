package com.example.allan.inventory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    public static String cond[] = {"FAILED", "POOR", "AVERAGE", "GOOD"};
    public static ArrayList<InventoryLog> entries = new ArrayList<>();
    public static String Username = "";
    public static String Quality = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Home_fragment frag = new Home_fragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.pagePlace, frag);
        ft.commit();

        DBAdapter db = new DBAdapter(this);
        try {
//getContext.getFilesDir().getPath()
            String destPath = "/data/data/" + getPackageName() +
                    "/databases";

            File f = new File(destPath);
            if (!f.exists()) {
                f.mkdirs();

                //f.createNewFile();

                //---copy the db from the assets folder into
                // the databases folder---
                CopyDB(getBaseContext().getAssets().open("inventorylog"),
                        new FileOutputStream(destPath + "/inventorylog"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //---get all contacts---
        db.open();
        // int itemcount = db.getItemcount();

        Cursor c = db.getAlllogs();
        if (c.moveToFirst()) {
            do {

                String name = c.getString(c.getColumnIndex("username"));
                String time = c.getString(c.getColumnIndex("logtime"));
                String invoiceNumber = c.getString(c.getColumnIndex("refNumber"));
                String quality = c.getString(c.getColumnIndex("quality"));
                String latitude = c.getString(c.getColumnIndex("latitude"));
                String longitude = c.getString(c.getColumnIndex("longitude"));

                InventoryLog newLog = new InventoryLog(name, time, invoiceNumber, quality, latitude, longitude);
                MainActivity.entries.add(newLog);
                //DisplayContact(c);
            } while (c.moveToNext());
        }
        db.close();
    }

    public void CopyDB(InputStream inputStream,
                       OutputStream outputStream) throws IOException {
        //---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }


    public void addInvoices(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        inventory_fragment frag;
        frag = new inventory_fragment();
        fragmentTransaction.replace(R.id.pagePlace, frag).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuChoice(item);
    }

    private Boolean MenuChoice(MenuItem item) {
        DBAdapter db = new DBAdapter(this);
        switch (item.getItemId()) {
            case R.id.profile:
                FragmentManager fragmentmanager = getFragmentManager();
                FragmentTransaction ft = fragmentmanager.beginTransaction();
                Profile_Fragment pf = new Profile_Fragment();
                ft.replace(R.id.pagePlace, pf);
                ft.commit();
                return true;

            case R.id.deletedb:
                db.open();
                db.deletealllogs();
                db.close();
                Toast.makeText(MainActivity.this, "Database has been deleted", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.save:

                db.open();
                db.deletealllogs();
                Iterator itr = MainActivity.entries.iterator();
                //Iterator over array and save each entry to the database.
                while (itr.hasNext()) {
                    InventoryLog inventoryitem = (InventoryLog) itr.next();
                    db.insertLog(inventoryitem.getTime(), inventoryitem.getName(),
                            inventoryitem.getReferenceNum(), inventoryitem.getQuality(),
                            inventoryitem.getLatitude(), inventoryitem.getLongitude());
                }
                Toast.makeText(MainActivity.this, "Data Has Been Saved", Toast.LENGTH_SHORT).show();
                int itemcount = db.getItemcount();
                Toast.makeText(MainActivity.this, itemcount + " items in the database", Toast.LENGTH_SHORT).show();
                db.close();
                return true;

            case R.id.send:
                sendMail();
                return true; // end case statement

        } //end switch

        return false;
    } // end menu choice

    private void sendMail() {

        String mSub = "New Logger data";
        String mMessage = Username + "\n";

        Iterator itr = entries.iterator();
        while (itr.hasNext()) {
            InventoryLog item = (InventoryLog) itr.next();

            String itemLog = item.getName() + " " + item.getTime() + " \nLatitude:-" +
                    item.getLatitude() + " \nLongitude:-" + item.getLongitude() + " " +
                    item.getReferenceNum() + " " + item.getQuality() + "\n";

            mMessage = mMessage + itemLog;
        }

//        PackageManager pm = getPackageManager();
        try {
            Toast.makeText(MainActivity.this, "Please Wait", Toast.LENGTH_LONG).show();
            deleteAllData();
            Intent mailIntent = new Intent(Intent.ACTION_SEND);
            mailIntent.setType("text/plain");

            //Check if package exists or not. If not then code
            //in catch block will be called
            mailIntent.setPackage("com.google.android.gm");
            mailIntent.putExtra(Intent.EXTRA_SUBJECT, mSub);
            mailIntent.putExtra(Intent.EXTRA_TEXT, mMessage);

            startActivity(Intent.createChooser(mailIntent, "Share with"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void deleteAllData() {
        DBAdapter db = new DBAdapter(this);
        db.open();
        db.deletealllogs();
        db.close();
        entries.clear();
    }

    @Override
    public void onBackPressed() {

        final Context ctx = this;
        new AlertDialog.Builder(ctx)
                .setTitle("Exit")
                .setMessage("Are you Sure you Want to Exit")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        DBAdapter db = new DBAdapter(ctx);
                        db.open();
                        int rowdel = db.deletealllogs();
                        Toast.makeText(ctx, rowdel + "Items were cleared from the database before update", Toast.LENGTH_SHORT).show();
                        Iterator itr = MainActivity.entries.iterator();
                        while (itr.hasNext()) {
                            InventoryLog item = (InventoryLog) itr.next();
                            db.insertLog(item.getTime(), item.getName(), item.getReferenceNum(),
                                    item.getQuality(), item.getLatitude(), item.getLongitude());
                        }
                        MainActivity.entries.clear();
                        db.close();


                        finish();
                    }
                }).create().show();
    }

    public static boolean isEmpty(TextView txtview) {

        return txtview.getText().toString().trim().length() == 0;
    }
}

