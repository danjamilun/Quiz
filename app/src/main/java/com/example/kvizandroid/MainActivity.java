package com.example.kvizandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kvizandroid.Common.Common;
import com.example.kvizandroid.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText edtNewEmail, edtNewPassword, edtNewUser;
    EditText edtUser, edtPassword;

    Button Sign_up, Sign_in;
    Intent homeActivity;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");

        edtUser=(EditText)findViewById(R.id.edtUser);
        edtPassword=(EditText)findViewById(R.id.edtPassword);

        Sign_in=(Button) findViewById(R.id.btn_sign_in);
        Sign_up=(Button) findViewById(R.id.btn_sign_up);


        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();
            }
        });

        Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(edtUser.getText().toString(), edtPassword.getText().toString());
            }
        });


    }

    private void signIn(final String user, String pwd)
    {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user).exists())
                {
                    if(!user.isEmpty())
                    {
                        User login=dataSnapshot.child(user).getValue(User.class);
                        if(login.getPassword().equals(pwd))
                           homeActivity= new Intent(MainActivity.this, Home.class);//ako je sve oke
                           Common.currentUser=login;
                           startActivity(homeActivity);
                           finish();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Unesite korisnicko ime i lozinku", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(MainActivity.this, "Korisnik ne postoji", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showSignUpDialog(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Sign up");
        alertDialog.setMessage("Ispunite informacije");

        LayoutInflater inflater=this.getLayoutInflater();
        View sign_up_layout=inflater.inflate(R.layout.sign_up_layout, null);

        edtNewUser=(EditText) sign_up_layout.findViewById(R.id.edtNewUser);
        edtNewEmail=(EditText) sign_up_layout.findViewById(R.id.edtNewEmail);
        edtNewPassword=(EditText) sign_up_layout.findViewById(R.id.edtNewPassword);


        alertDialog.setView(sign_up_layout);

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                User user =  new User(edtNewUser.getText().toString(),
                        edtNewPassword.getText().toString(),
                        edtNewEmail.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(user.getUserName()).exists())
                            Toast.makeText(MainActivity.this, "Korisnik postoji", Toast.LENGTH_LONG).show();
                        else {
                            users.child(user.getUserName())
                                    .setValue(user);
                            Toast.makeText(MainActivity.this, "Uspjesna registracija", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                });
                dialogInterface.dismiss();

            }
        });
        alertDialog.show();

    }
}