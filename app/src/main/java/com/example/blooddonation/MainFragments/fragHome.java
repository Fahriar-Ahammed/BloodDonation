package com.example.blooddonation.MainFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.HomeActivity;
import com.example.blooddonation.R;
import com.example.blooddonation.dataModel.bloodRequestModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragHome extends Fragment {

    private static final String TAG = "fragHome";
    Context mContext;
    public NavigationView navigation;
    ExtendedFloatingActionButton btnFloatingButton;
    public DrawerLayout drawer;
    ListView listView;
    String token;

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
        listView = view.findViewById(R.id.listView);

        List<bloodRequestModel> myDataOnList = new ArrayList<>();

        HomeActivity activity = new HomeActivity();


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
        Log.d(TAG, "onCreateView: @@@@@@@@@@@@@@                         Token"+token);


        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        String url ="https://blood.dreamitdevlopment.com/public/api/blood-request/view/1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d(TAG, "onResponse: @@@@@@@@@@@@@@@@@@@@@@ Response " + response);

                bloodRequestModel bloodRequestModel = new bloodRequestModel();

                try {

                    bloodRequestModel.setUser_id(response.getInt("user_id"));
                    bloodRequestModel.setPatient_name(response.getString("patient_name"));
                    bloodRequestModel.setPatient_diagnosis(response.getString("patient_diagnosis"));
                    bloodRequestModel.setBlood_group(response.getString("blood_group"));
                    bloodRequestModel.setHospital_name(response.getString("hospital_name"));
                    bloodRequestModel.setGender(response.getString("gender"));
                    bloodRequestModel.setDivision(response.getString("division"));
                    bloodRequestModel.setDistrict(response.getString("district"));
                    bloodRequestModel.setUpazila(response.getString("upazila"));

                    myDataOnList.add(bloodRequestModel);
                    ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, myDataOnList);
                    listView.setAdapter(arrayAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);









// Test Code
/*
        //String url ="https://dreamit.ishoppis.com/public/api/profile/list-boost/?token="+token;
        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/view/1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Log.d(TAG, "onResponse: @@@@@@@@@@@@@@@@ Response          " + response);

                    for (int i = 0; i<=100; i++){

                        bloodRequestModel bloodRequestModel = new bloodRequestModel();

                        //JSONObject jsonObject = response.getJSONObject(i);

//                        bloodRequestModel.setUser_id(jsonObject.getInt("user_id"));
//                        bloodRequestModel.setPatient_name(jsonObject.getString("patient_name"));
//                        bloodRequestModel.setPatient_diagnosis(jsonObject.getString("patient_diagnosis"));
//                        bloodRequestModel.setBlood_group(jsonObject.getString("blood_group"));
//                        bloodRequestModel.setHospital_name(jsonObject.getString("hospital_name"));
//                        bloodRequestModel.setGender(jsonObject.getString("gender"));
//                        bloodRequestModel.setDivision(jsonObject.getString("division"));
//                        bloodRequestModel.setDistrict(jsonObject.getString("district"));
//                        bloodRequestModel.setUpazila(jsonObject.getString("upazila"));


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
 */

        // FloatingAction Button
        /*
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
         */
        return view;
    }

}