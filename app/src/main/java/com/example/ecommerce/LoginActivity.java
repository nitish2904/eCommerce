package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText InputPhoneNumber,InputPassword;
    private Button LoginButton;
    private ProgressDialog LoadingBar;
    private String ParentDbName = "Users";
    private CheckBox ChkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InputPhoneNumber = (EditText)findViewById(R.id.login_phone_number_input);
        InputPassword = (EditText)findViewById(R.id.login_password_input);
        LoginButton = (Button)findViewById(R.id.login_btn);
        LoadingBar = new ProgressDialog(this);
        ChkBoxRememberMe = (CheckBox)findViewById(R.id.remember_me_chkb);
        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

    }

    private void LoginUser() {
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "please input the phone number! .. ", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "please input your password!", Toast.LENGTH_SHORT).show();
        }
        else{
            LoadingBar.setTitle("login account");
            LoadingBar.setMessage("please wait while we verify the credentials");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();

            AllowAccessToAccount(phone,password);
        }

    }

    private void AllowAccessToAccount(final String phone, final String password) {
        if (ChkBoxRememberMe.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(ParentDbName).child(phone).exists()){
                    Users Usersdata = dataSnapshot.child(ParentDbName).child(phone).getValue(Users.class);
                    if(Usersdata.getPhone().equals(phone)){
                        if (Usersdata.getPassword().equals(password)){
                            Toast.makeText(LoginActivity.this, "Logged in Successfully ..", Toast.LENGTH_SHORT).show();
                            LoadingBar.dismiss();
                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            LoadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "wrong Password! ..", Toast.LENGTH_SHORT).show();
                        }
                    }
                    
                }
                else{
                    Toast.makeText(LoginActivity.this, "The Account with this phone Number dosenot exists ..", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
