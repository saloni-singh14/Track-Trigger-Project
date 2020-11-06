package com.example.myapplication3;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Login extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_login,container,false);
        Button loginbtn;
        EditText uname,pass;
        TextView forgpswd;
        float v=0;
        uname=root.findViewById(R.id.username);
        pass=root.findViewById(R.id.password);
        loginbtn=root.findViewById(R.id.Loginbtn);
        forgpswd=root.findViewById(R.id.forgPass);

        uname.setTranslationX(800);
        pass.setTranslationX(800);
        loginbtn.setTranslationX(800);
        forgpswd.setTranslationX(800);


        uname.setAlpha(v);
        pass.setAlpha(v);
        loginbtn.setAlpha(v);
        forgpswd.setAlpha(v);


        uname.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgpswd.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        loginbtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = uname.getText().toString().trim();
                String pswd = pass.getText().toString().trim();

                if (TextUtils.isEmpty(user)) {
                    uname.setError("This is a required field");
                    return;
                } else if (TextUtils.isEmpty(pswd)) {
                    pass.setError("This is a required field");
                    return;
                } else {
                    isUser();
                }
            }

            private void isUser() {

                final String userEnteredUsername = uname.getText().toString().trim();
                final String userEnteredPassword = pass.getText().toString().trim();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                            if (passwordFromDB.equals(userEnteredPassword)) {

                                String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                                String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                intent.putExtra("name", nameFromDB);
                                intent.putExtra("username", usernameFromDB);
                                intent.putExtra("password", passwordFromDB);
                                startActivity(intent);
                            } else {
                                pass.setError("Wrong Password");
                                pass.requestFocus();
                            }
                        } else {

                            uname.setError("No such User exist");
                            uname.requestFocus();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });





        return root;

    }}
