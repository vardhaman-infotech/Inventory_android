package com.example.allan.inventory;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.allan.inventory.dummy.DummyContent;
import com.example.allan.inventory.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemFragment extends Fragment {

    public ItemFragment() {
    }


    @Override
    public void onStart() {
        super.onStart();
        ListView lv = getActivity().findViewById(R.id.list);
        ArrayList<String> lv_arr = new ArrayList<>();
        Iterator itr = MainActivity.entries.iterator();
        while (itr.hasNext()) {
            InventoryLog item = (InventoryLog) itr.next();

            String itemLog = item.getName() + " " + item.getTime() + " \nLatitude:-" +
                    item.getLatitude() + " \nLongitude:-" + item.getLongitude() + " " +
                    item.getReferenceNum() + " " + item.getQuality();
            lv_arr.add(itemLog);

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, lv_arr);
        lv.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

}
