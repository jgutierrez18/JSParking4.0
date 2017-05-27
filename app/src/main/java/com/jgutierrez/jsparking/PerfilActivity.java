package com.jgutierrez.jsparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView tvEmail , tvNombre ,tvTelefono ;
    private Usuario usuario ;
    private Button btnCerrarSesion;
    DatabaseReference databaseReference;
    DatabaseReference Refusuario;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {

            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvTelefono = (TextView) findViewById(R.id.tvTelefono);
        tvEmail = (TextView) findViewById(R.id.tvEmail);


        tvEmail.setText("'"+ user.getEmail());




        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(this);
        getUsuario();
/*
        Refusuario.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot usuario = (DataSnapshot) dataSnapshot.getChildren();

                tvNombre.setText("'" + usuario.child("Nombre"));
                tvTelefono.setText("'" + usuario.child("Telefono"));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

 */
    }
    public void getUsuario() {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot registro : dataSnapshot.getChildren())

                    if (registro.child("Email").getValue(String.class).equals(firebaseAuth.getCurrentUser().getEmail())) {


                        usuario = registro.getValue(Usuario.class);
                        System.out.println(usuario.getUid());


                        DatabaseReference Refusuario = registro.getRef();
                        break;
                    } else {
                        System.out.println("no hay email");
                    }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // Read from the database
        Refusuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                DataSnapshot usuario = (DataSnapshot) dataSnapshot.getChildren();

                tvNombre.setText("'" + usuario.child("Nombre"));
                tvTelefono.setText("'" + usuario.child("Telefono"));

                Log.d("", "Value is: " + tvNombre + tvTelefono);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnCerrarSesion) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));

        }

    }
}
