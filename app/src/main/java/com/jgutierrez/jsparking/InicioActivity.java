package com.jgutierrez.jsparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InicioActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnlogin, btnRegistrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        btnlogin= (Button)findViewById(R.id.btnlogin);
        btnRegistrate= (Button)findViewById(R.id.btnRegistrate);

        btnlogin.setOnClickListener(this);
        btnRegistrate.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnlogin){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        if (v == btnRegistrate){
            finish();
            startActivity(new Intent(this,RegistroActivity.class));
        }
    }
}
