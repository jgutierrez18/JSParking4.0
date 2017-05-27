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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button btnregistro;
    private EditText etEmail, etClave, etNombre, etTelefono ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        btnregistro= (Button) findViewById(R.id.btnregistro);
        etNombre= (EditText) findViewById(R.id.etNombre);
        etTelefono= (EditText) findViewById(R.id.etTelefono);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etClave= (EditText) findViewById(R.id.etClave);

        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){

            finish();
            startActivity(new Intent(getApplicationContext(),PerfilActivity.class));
        }
        btnregistro.setOnClickListener(this);

    }
    private void Resgistro() {

        String Email = etEmail.getText().toString().trim();
        String Clave = etClave.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {

            Toast.makeText(this, "Es necesario un Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Clave)) {

            Toast.makeText(this, "Es necesario que digite una Clave de Email", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email, Clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference nodoUsuario = database.getReference("Usuario");
                    Usuario usuario = new Usuario();


                    usuario.setNombre(etNombre.getText().toString().trim());
                    usuario.setTelefono(etTelefono.getText().toString().trim());

                    nodoUsuario.push().setValue(usuario);

                    finish();

                    startActivity(new Intent(getApplicationContext(), PerfilActivity.class));

                    Toast.makeText(RegistroActivity.this, "registrado exitosamente", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegistroActivity.this, "Error, no se pudo hacer el registro de usuario", Toast.LENGTH_SHORT).show();

                }


            }

        });
    }

        @Override
    public void onClick(View v) {

            if (v == btnregistro){
                Resgistro();
            }

        }
}
