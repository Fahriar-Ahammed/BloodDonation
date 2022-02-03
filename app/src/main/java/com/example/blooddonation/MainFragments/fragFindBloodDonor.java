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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.blooddonation.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragFindBloodDonor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragFindBloodDonor extends Fragment {

    private static final String TAG = "fragFindBloodDonor";
    ConstraintLayout findBloodDonorXML;
    Context mContext;
    AutoCompleteTextView spinnerBloodGroup;
    TextInputEditText etPatientFullName, etHospitalName, etHospitalLocation, etContactNumber;
    Button btnSubmitBloodRequest;

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

        spinnerBloodGroup = view.findViewById(R.id.spinnerBloodGroup);

        etPatientFullName = view.findViewById(R.id.etPatientFullName);
        etHospitalName = view.findViewById(R.id.etHospitalName);
        etHospitalLocation = view.findViewById(R.id.etHospitalLocation);
        etContactNumber = view.findViewById(R.id.etContactNumber);

        btnSubmitBloodRequest = view.findViewById(R.id.btnSubmitBloodRequest);

        // Blood Group Spinner
        ArrayAdapter<CharSequence> bloodGroupAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.blood_group,
                android.R.layout.simple_spinner_item);
        bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(bloodGroupAdapter);

        // onClick Contact Number Hint Changer
        etContactNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //         android:hint="Contact Number"

                if (view.hasFocus()){
                    etContactNumber.setHint("Without +88");
                }else if (!view.hasFocus()){
                    etContactNumber.setHint("");
                }
            }
        });

        // Submit Button Config
        btnSubmitBloodRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@@                         Submit Button Clicked");

                emptyValidation();

                // Patient Name
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                        Patient Name : " + patientName);

                // Hospital Name
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                        Hospital Name : " + hospitalName);

                // Hospital Location
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                         Hospital Location : " + hospitalLocation);

                // Contact Number
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                         Contact Number : " + contactNumber);

                // Blood Group
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                         Blood Group : " + requestingBloodGroup);

            }
        });

        return view;
    }

    // Empty Validator
    private void emptyValidation() {

        Log.d(TAG, "emptyValidation: @@@@@@@@@@@@@@@@@                   Empty Validating Starting here  : ");

        requestingBloodGroup = spinnerBloodGroup.getText().toString();

        patientName = etPatientFullName.getText().toString();
        hospitalName = etHospitalName.getText().toString();
        hospitalLocation = etHospitalLocation.getText().toString();
        contactNumber = etContactNumber.getText();

        numberValidator = etContactNumber.getText().length();

        if (patientName.isEmpty()){
            etPatientFullName.requestFocus();
            etPatientFullName.setError("Please Enter Patient name");
        }else if (hospitalName.isEmpty()){
            etHospitalName.requestFocus();
            etHospitalName.setError("Enter Hospital Name");
        }else if (hospitalLocation.isEmpty()){
            etHospitalLocation.requestFocus();
            etHospitalLocation.setError("Enter Hospital Location");
        }else if (contactNumber == null){
            etContactNumber.requestFocus();
            etContactNumber.setError("Enter Contact Number");
        }else if (numberValidator != 11){
            etContactNumber.requestFocus();
            etContactNumber.setError("Enter a valid number");
        }else if (requestingBloodGroup.isEmpty()){
            Toast.makeText(mContext, "Please Spacify the blood group.", Toast.LENGTH_SHORT).show();
        }else{
//            Toast.makeText(mContext, "Request Successful", Toast.LENGTH_SHORT).show();

            Snackbar bar = Snackbar.make(getView().findViewById(R.id.findBloodDonorXML), " ", Snackbar.LENGTH_LONG);
            bar.setText("Request Successful");
            bar.show();
        }
    }
}