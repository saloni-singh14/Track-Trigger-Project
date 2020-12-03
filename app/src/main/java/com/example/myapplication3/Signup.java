package com.example.myapplication3;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends Fragment implements AdapterView.OnItemSelectedListener {
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String[] userType = { "Select User Type", "Working Professionals",
            "Job Seekers", "Home Makers","others"};




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_signup,container,false);
        EditText mFullName,mUsername,mPassword,mPassword1;
        Spinner mProf;
        Button Registerbtn;
        float v=0;


            mFullName=root.findViewById(R.id.mname);
        mPassword=root.findViewById(R.id.mpassword);
        mPassword1=root.findViewById(R.id.mpassword1);
        mUsername=root.findViewById(R.id.musername);

        mProf=(Spinner)root.findViewById(R.id.mprof);
        mProf.setOnItemSelectedListener(this);


        ArrayAdapter ad= new ArrayAdapter(root.getContext(), android.R.layout.simple_spinner_item, userType);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mProf.setAdapter(ad);




        Registerbtn=root.findViewById(R.id.regbtn);

        mFullName.setTranslationX(800);
        mPassword.setTranslationX(800);
        mPassword1.setTranslationX(800);
        mUsername.setTranslationX(800);

        mProf.setTranslationX(800);


        mFullName.setAlpha(v);
        mPassword.setAlpha(v);
        mPassword1.setAlpha(v);
        mUsername.setAlpha(v);

        mProf.setAlpha(v);


        mFullName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();
        mPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mPassword1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mUsername.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();

        mProf.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        Registerbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                String username=mUsername.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String password1=mPassword1.getText().toString().trim();
                String fullname=mFullName.getText().toString().trim();
                String profession=mProf.getSelectedItem().toString().trim();



                if(TextUtils.isEmpty(fullname)){
                    mFullName.setError("This is a required field");
                    return;
                }

                if(TextUtils.isEmpty(username)){
                    mUsername.setError("This is a required field");
                    return;
                }
                String regex1="^[A-Za-z]\\w{5,29}$";
                Pattern p1 = Pattern.compile(regex1);
                Matcher m1=p1.matcher(username);
                if(!m1.matches()){
                    mUsername.setError("Invalid Username");
                    return;
                }


                if(TextUtils.isEmpty(password)){
                    mPassword.setError("This is a required field");
                    return;
                }

                String regex = "^(?=.*[0-9])"
                        + "(?=.*[a-z])(?=.*[A-Z])"
                        + "(?=.*[@#$%^&+=])"
                        + "(?=\\S+$).{8,20}$";
                Pattern p = Pattern.compile(regex);
                Matcher m=p.matcher(password);
                if(!m.matches()){
                    if(password.length()<8) mPassword.setError("Password too short");
                    else if(password.length()>=20) mPassword.setError("Password too long");
                    else mPassword.setError("Password too weak");
                    return;
                }


                if(TextUtils.isEmpty(password1)){
                    mPassword1.setError("This is a required field");
                    return;
                }
                if(!password.equals(password1)){
                    mPassword1.setError("Passwords do not match");
                    return;
                }


                UserHelperClass helperClass = new UserHelperClass(fullname, username, password,profession);
                reference.child(username).setValue(helperClass);

                Intent intent=new Intent(getActivity(),verification.class);
                startActivity(intent);

            }});


        return root;

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
