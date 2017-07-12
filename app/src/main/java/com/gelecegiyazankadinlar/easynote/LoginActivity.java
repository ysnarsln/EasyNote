package com.gelecegiyazankadinlar.easynote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername, etPassword;
    private Button btLogin, btSignup;
    private CheckBox cbSavePassword;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private FirebaseAuth auth;

    private String SP_NAME = "LOGIN_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);

        cbSavePassword = (CheckBox) findViewById(R.id.cb_save_password);

        btLogin = (Button) findViewById(R.id.bt_signin);
        btSignup = (Button) findViewById(R.id.bt_signup);

        btLogin.setOnClickListener(this);
        btSignup.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

        mSharedPreferences = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        String spUsername = mSharedPreferences.getString("username", "");
        String spPassword = mSharedPreferences.getString("password", "");
        etUsername.setText(spUsername);
        etPassword.setText(spPassword);
        if (!spUsername.isEmpty())
            cbSavePassword.setChecked(true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onClick(View view) {

        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        switch (view.getId()) {

            case R.id.bt_signin:

                if (!username.isEmpty() && !password.isEmpty()) {

                    auth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        if (cbSavePassword.isChecked()) {
                                            mEditor = mSharedPreferences.edit();
                                            mEditor.putString("username", username);
                                            mEditor.putString("password", password);
                                            mEditor.commit();
                                        } else {
                                            mEditor = mSharedPreferences.edit();
                                            mEditor.clear().commit();
                                        }

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {

                                        Toast.makeText(LoginActivity.this, "Hatalı giriş", Toast.LENGTH_SHORT).show();

                                    }

                                    if (task.getException() != null) {
                                        Log.i("LoginAct", task.getException().toString());
                                    }

                                }
                            });

                } else {

                    Toast.makeText(this, "Kullanıcı veya şifre boş olamaz", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.bt_signup:

                if (!username.isEmpty() && !password.isEmpty()) {

                    auth.createUserWithEmailAndPassword(username, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(LoginActivity.this, "Başarılı", Toast.LENGTH_SHORT).show();

                                    } else {

                                        Toast.makeText(LoginActivity.this, "Hatalı giriş", Toast.LENGTH_SHORT).show();

                                    }

                                    if (task.getException() != null) {
                                        Log.i("LoginAct", task.getException().toString());
                                    }

                                }
                            });

                } else {

                    Toast.makeText(this, "Kullanıcı veya şifre boş olamaz", Toast.LENGTH_SHORT).show();

                }
                break;

        }

    }

}
