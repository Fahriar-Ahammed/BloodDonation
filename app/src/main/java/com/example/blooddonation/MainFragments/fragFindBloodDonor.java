package com.example.blooddonation.MainFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragFindBloodDonor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragFindBloodDonor extends Fragment {

    private static final String TAG = "fragFindBloodDonor";
    ConstraintLayout findBloodDonorXML;
    Context mContext;
    AutoCompleteTextView spinnerBloodGroup, spinnerGender, spinnerDivision, spinnerDistrict, spinnerUpazila;
    TextInputEditText etPatientName, etPatientDiagnosis, etHospitalName;
    Button btnRegister;
    private ArrayList<String> divisions ,districts ,upazilas;

    String requestingBloodGroup;
    String patientName, hospitalName, hospitalLocation;
    Editable contactNumber;
    int numberValidator;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragFindBloodDonor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragFindBloodDonor.
     */
    // TODO: Rename and change types and number of parameters
    public static fragFindBloodDonor newInstance(String param1, String param2) {
        fragFindBloodDonor fragment = new fragFindBloodDonor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_find_blood_donor, container, false);

        etPatientName = view.findViewById(R.id.etPatientName);
        etPatientDiagnosis = view.findViewById(R.id.etPatientDiagnosis);
        etHospitalName = view.findViewById(R.id.etHospitalName);

        spinnerBloodGroup = view.findViewById(R.id.spinnerBloodGroup);
        spinnerGender = view.findViewById(R.id.spinnerGender);
        spinnerDivision = view.findViewById(R.id.spinnerDivision);
        spinnerDistrict = view.findViewById(R.id.spinnerDistrict);
        spinnerUpazila = view.findViewById(R.id.spinnerUpazila);

        btnRegister = view.findViewById(R.id.btnRegister);

        divisions = new ArrayList<String>();
        districts = new ArrayList<String>();
        upazilas = new ArrayList<String>();

        getDivisionData();

        // Blood Group Spinner
        ArrayAdapter<CharSequence> bloodGroupAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.blood_group,
                android.R.layout.simple_spinner_item);
        bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(bloodGroupAdapter);

        spinnerDivision.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDistrictData();
            }
        });

        spinnerDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getUpazilaData();
            }
        });



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@@                         Submit Button Clicked");

//                emptyValidation();


            }
        });

        return view;
    }

    private void getDivisionData() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://blood.dreamitdevlopment.com/public/api/division";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("division");

                            Log.d(TAG, "onResponse: @@@@@@@" + jsonArray);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject division = jsonArray.getJSONObject(i);

                                divisions.add(division.getString("bn_name"));
                            }

                            /*for address select*/
                            ArrayAdapter<String> divisionAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, divisions);
                            spinnerDivision.setAdapter(divisionAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:    " + error);
            }
        });
        queue.add(stringRequest);
    }

    private void getDistrictData() {
        districts.clear();
        spinnerDistrict.setText(null);

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://blood.dreamitdevlopment.com/public/api/district?district_id=" + spinnerDivision.getText().toString();

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("district");

                            Log.d(TAG, "onResponse: @@@@@@@" + jsonArray);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject division = jsonArray.getJSONObject(i);

                                districts.add(division.getString("bn_name"));
                            }

                            /*for address select*/
                            ArrayAdapter<String> divisionAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, districts);
                            spinnerDistrict.setAdapter(divisionAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:    " + error);
            }
        });
        queue.add(stringRequest);
    }

    private void getUpazilaData(){

        upazilas.clear();
        spinnerUpazila.setText(null);

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://blood.dreamitdevlopment.com/public/api/upazila?district_id=" + spinnerDistrict.getText().toString();

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("upazila");

                            Log.d(TAG, "onResponse: @@@@@@@" + jsonArray);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject upazila = jsonArray.getJSONObject(i);

                                upazilas.add(upazila.getString("bn_name"));
                            }

                            /*for address select*/
                            ArrayAdapter<String> upazilaAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, upazilas);
                            spinnerUpazila.setAdapter(upazilaAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:    " + error);
            }
        });
        queue.add(stringRequest);
    }

// Empty Validator
//    private void emptyValidation() {
//
//        Log.d(TAG, "emptyValidation: @@@@@@@@@@@@@@@@@                   Empty Validating Starting here  : ");
//
//        requestingBloodGroup = spinnerBloodGroup.getText().toString();
//
//        patientName = etPatientFullName.getText().toString();
//        hospitalName = etHospitalName.getText().toString();
//        hospitalLocation = etHospitalLocation.getText().toString();
//        contactNumber = etContactNumber.getText();
//
//        numberValidator = etContactNumber.getText().length();
//
//        if (patientName.isEmpty()){
//            etPatientFullName.requestFocus();
//            etPatientFullName.setError("Please Enter Patient name");
//        }else if (hospitalName.isEmpty()){
//            etHospitalName.requestFocus();
//            etHospitalName.setError("Enter Hospital Name");
//        }else if (hospitalLocation.isEmpty()){
//            etHospitalLocation.requestFocus();
//            etHospitalLocation.setError("Enter Hospital Location");
//        }else if (contactNumber == null){
//            etContactNumber.requestFocus();
//            etContactNumber.setError("Enter Contact Number");
//        }else if (numberValidator != 11){
//            etContactNumber.requestFocus();
//            etContactNumber.setError("Enter a valid number");
//        }else if (requestingBloodGroup.isEmpty()){
//            Toast.makeText(mContext, "Please Spacify the blood group.", Toast.LENGTH_SHORT).show();
//        }else{
////            Toast.makeText(mContext, "Request Successful", Toast.LENGTH_SHORT).show();
//
//            Snackbar bar = Snackbar.make(getView().findViewById(R.id.findBloodDonorXML), " ", Snackbar.LENGTH_LONG);
//            bar.setText("Request Successful");
//            bar.show();
//        }
//    }



    // Submit Button Config

// onClick Hint Changer
    /*
    //        etContactNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                //         android:hint="Contact Number"
//
//                if (view.hasFocus()){
//                    etContactNumber.setHint("Without +88");
//                }else if (!view.hasFocus()){
//                    etContactNumber.setHint("");
//                }
//            }
//        });
     */
}

/*
<!--    Patient Full Name -->
    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/textInputLayout3"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="100dp"
        android:layout_marginRight="20dp"
        android:layout_marginLeft="20dp"
        android:hint="Patient Full Name"
        app:boxBackgroundColor="@color/white"
        android:backgroundTint="@color/white"
        android:textColorHint="@color/black"

        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:startIconDrawable="@drawable/ic_identity">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/etPatientFullName"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="text" />

    </com.google.android.material.textfield.TextInputLayout>


<!--    Hospital Name-->
    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/textout3"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Hospital Name"
        app:boxBackgroundColor="@color/white"
        android:layout_marginTop="15dp"
        android:layout_marginRight="20dp"
        android:layout_marginLeft="20dp"
        android:textColorHint="@color/black"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textInputLayout3"
        app:startIconDrawable="@drawable/ic_hospital">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/etHospitalName"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="text" />

    </com.google.android.material.textfield.TextInputLayout>

<!--    Address-->
    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/bckascn"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="20dp"
        android:layout_marginTop="15dp"
        android:layout_marginRight="20dp"
        android:hint="Address"
        app:boxBackgroundColor="@color/white"
        android:textColorHint="@color/black"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textout3"
        app:startIconDrawable="@drawable/ic_location">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/etHospitalLocation"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="text" />

    </com.google.android.material.textfield.TextInputLayout>


<!--    Contact Number-->
    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/text3"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="15dp"
        android:layout_marginRight="20dp"
        android:layout_marginLeft="20dp"
        android:hint="Contact Number"
        android:textColorHint="@color/black"
        app:boxBackgroundColor="@color/white"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/bckascn"
        app:startIconDrawable="@drawable/ic_phone">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/etContactNumber"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="number" />

    </com.google.android.material.textfield.TextInputLayout>


    <!--    Blood Type-->

    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/customLayout"
        style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox.ExposedDropdownMenu"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="20dp"
        android:layout_marginTop="10dp"
        android:layout_marginRight="20dp"
        android:hint="Blood Group"
        app:boxBackgroundColor="@color/white"
        android:textColorHint="@color/black"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/text3"
        app:startIconDrawable="@drawable/ic_blood">

        <androidx.appcompat.widget.AppCompatAutoCompleteTextView
            android:id="@+id/spinnerBloodGroup"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:clickable="true"
            tools:ignore="KeyboardInaccessibleWidget,SpeakableTextPresentCheck" />
    </com.google.android.material.textfield.TextInputLayout>


    <Button
        android:id="@+id/btnSubmitBloodRequest"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="15dp"
        android:layout_marginRight="15dp"
        android:backgroundTint="@color/redDBG"
        android:padding="15dp"
        android:text="Add Blood Request"
        android:textColor="@color/black"
        android:textSize="18sp"
        android:textStyle="bold|italic"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.551"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/customLayout"
        app:layout_constraintVertical_bias="0.157" />
 */