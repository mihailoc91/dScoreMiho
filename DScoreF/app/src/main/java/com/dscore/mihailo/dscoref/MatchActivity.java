package com.dscore.mihailo.dscoref;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import model.Delegat;
import model.Match;

public class MatchActivity extends AppCompatActivity {

    //Staticki int id koji sluzi da bi se preuzeo ID iz prethodne aktivnosti (MatchesActivity) i predstavlja ID odabranog meca
    static int id;
    static Delegat delegatS;
    static int idDelegataS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        //Uzimanje referenci na textView-ove koji sluze da bi se prikazao naziv timova
        TextView textView1 = (TextView)findViewById(R.id.textView1);
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        TextView textView4 = (TextView)findViewById(R.id.textView4);
        TextView textView5 = (TextView)findViewById(R.id.textView5);
        TextView textView6 = (TextView)findViewById(R.id.textView6);
        TextView textView7 = (TextView)findViewById(R.id.textView7);
        TextView textView8 = (TextView)findViewById(R.id.textView8);
        TextView textView9 = (TextView)findViewById(R.id.textView9);
        TextView textView10 = (TextView)findViewById(R.id.textView10);



        //Preuzimanje vrednosti iz prethodne aktivnosti
        Intent intent = getIntent();
        delegatS= (Delegat) intent.getSerializableExtra("delegat");
        idDelegataS = intent.getIntExtra("idDelegata",0);
        id = intent.getIntExtra("id",0);
       //Postavljanje naziva za textView-ove za domacina
        String firstTeam = intent.getStringExtra("firstTeam");
        textView1.setText(firstTeam);
        textView3.setText(firstTeam);
        textView5.setText(firstTeam);
        textView7.setText(firstTeam);
        textView9.setText(firstTeam);
        //Postavljanje naziva za textView-ove za gosta
        String secondTeam = intent.getStringExtra("secondTeam");
        textView2.setText(secondTeam);
        textView4.setText(secondTeam);
        textView6.setText(secondTeam);
        textView8.setText(secondTeam);
        textView10.setText(secondTeam);

    }

    //onClick metod za dugme Sacuvaj, preuzima unete vrednosti i prosledjuje ih klasi Match da bi ih ona sacuvala u bazi
       public void sacuvajClicked(View view){
        EditText editText1 = (EditText)findViewById(R.id.editText1);
        EditText editText2 = (EditText)findViewById(R.id.editText2);
        EditText editText3 = (EditText)findViewById(R.id.editText3);
        EditText editText4 = (EditText)findViewById(R.id.editText4);
        EditText editText5 = (EditText)findViewById(R.id.editText5);
        EditText editText6 = (EditText)findViewById(R.id.editText6);
        EditText editText7 = (EditText)findViewById(R.id.editText7);
        EditText editText8 = (EditText)findViewById(R.id.editText8);
        EditText editText9 = (EditText)findViewById(R.id.editText9);
        EditText editText10 = (EditText)findViewById(R.id.editText10);

        String a1 = editText1.getText().toString().contentEquals("")?"0":editText1.getText().toString();
        String a2 = editText3.getText().toString().contentEquals("")?"0":editText3.getText().toString();
        String a3= editText5.getText().toString().contentEquals("")?"0":editText5.getText().toString();
        String a4= editText7.getText().toString().contentEquals("")?"0":editText7.getText().toString();
        String a_extra = editText9.getText().toString().contentEquals("")?"0":editText9.getText().toString();

        String b1 = editText2.getText().toString().contentEquals("")?"0":editText2.getText().toString();
        String b2 =editText4.getText().toString().contentEquals("")?"0":editText4.getText().toString();
        String b3 = editText6.getText().toString().contentEquals("")?"0":editText6.getText().toString();
        String b4 = editText8.getText().toString().contentEquals("")?"0":editText8.getText().toString();
        String b_extra = editText10.getText().toString().contentEquals("")?"0":editText10.getText().toString();
        try {
            new Match().execute("3",String.valueOf(id),a1,b1,a2,b2,a3,b3,a4,b4,a_extra,b_extra).get();
            Toast.makeText(this, "Uspesno sacuvan rezultat!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MatchActivity.this,MatchesActivity.class);
            intent.putExtra("delegat",delegatS);
            intent.putExtra("idDelegata",idDelegataS);
            startActivity(intent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
