package com.dscore.mihailo.dscoref;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import controller.Controller;
import model.Delegat;

public class MainActivity extends AppCompatActivity {


//onClick metod za dugme Sacuvaj
        public void loginClicked(View v) {
            String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();

            //Uzima delegata na osnovu unetog email-a
            Delegat delegat = Controller.login(email, MainActivity.this);

                    //proverava da li delegat postoji, ako postoji zapocinje novu aktivnost ako ne postoji obavestava korisnika da ne postoji korisnik sa tim email-om
                    if (delegat!=null) {

                        Intent intent = new Intent(this, LeagueActivity.class);
                        intent.putExtra("delegat",delegat);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "Ne postoji taj korisnik", Toast.LENGTH_LONG).show();
                    }


        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}

