package com.jgutierrez.jsparking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    private EditText etEmail, etClave;
    private Button btnlogin,btnRegistrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){

            finish();
            startActivity(new Intent(getApplicationContext(),PerfilActivity.class));
        }

        etEmail= (EditText) findViewById(R.id.etEmail);
        etClave= (EditText) findViewById(R.id.etClave);
        btnlogin= (Button)findViewById(R.id.btnlogin);
        btnRegistrate= (Button)findViewById(R.id.btnRegistrate);

        btnRegistrate.setOnClickListener(this);
        btnlogin.setOnClickListener(this);

    }

    public void IniciarSesion(){


        String Email =  etEmail.getText().toString().trim();
        String Clave =  etClave.getText().toString().trim();

        if (TextUtils.isEmpty(Email)){

            Toast.makeText(this,"Es necesario un Email",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Clave)){

            Toast.makeText(this,"Es necesario que digite una Clave de Email",Toast.LENGTH_SHORT).show();
            return;
        }


        firebaseAuth.signInWithEmailAndPassword(Email,Clave).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(),PerfilActivity.class));
                    Toast.makeText(LoginActivity.this, "Sesión Iniciada", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Email o Clave digitadas son Incorrecto", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v==btnlogin){

            IniciarSesion();
        }
        if (v == btnRegistrate){
            finish();
            startActivity(new Intent(this,RegistroActivity.class));
        }

    }
}
