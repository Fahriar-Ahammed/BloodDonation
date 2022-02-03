package com.example.blooddonation.MainFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.blooddonation.HomeActivity;
import com.example.blooddonation.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragHome extends Fragment {

    Context mContext;
    public NavigationView navigation;
    ExtendedFloatingActionButton btnFloatingButton;
    public DrawerLayout drawer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragHome.
     */
    // TODO: Rename and change types and number of parameters
    public static fragHome newInstance(String param1, String param2) {
        fragHome fragment = new fragHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_home, container, false);

        navigation = view.findViewById(R.id.navigation);
        drawer = view.findViewById(R.id.drawer);

        HomeActivity activity = new HomeActivity();

        btnFloatingButton = view.findViewById(R.id.btnFloatingButton);

        btnFloatingButton.setVisibility(View.VISIBLE);

        btnFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragFindBloodDonor findBloodDonor = new fragFindBloodDonor();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragHomeXML, findBloodDonor, "testTestTest")
                        .addToBackStack(null)
                        .commit();

                btnFloatingButton.setVisibility(View.GONE);

//                activity.navigationListner();

//                navigation.setCheckedItem(R.id.findBloodDonorXML);
            }
        });
        return view;
    }

}