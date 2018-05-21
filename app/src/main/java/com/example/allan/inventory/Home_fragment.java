package com.example.allan.inventory;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_fragment extends Fragment {


    public Home_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        final Button next = (Button) getActivity().findViewById(R.id.btnNext);
        final EditText a_user = (EditText) getActivity().findViewById(R.id.username);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = a_user.getText().toString();
                if (TextUtils.isEmpty(username)){
                   Toast.makeText(getActivity(), "Please Enter a User Name", Toast.LENGTH_SHORT).show();
                }else {
                    MainActivity.Username = a_user.getText().toString();
                    inventory_fragment frag = new inventory_fragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.pagePlace, frag);
                    ft.commit();
                }
                //              ..... this is were the click handler is implemented
            }
        });
    }

}
