package com.dscore.mihailo.dscoref;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import model.Delegat;

public class LeagueActivity extends AppCompatActivity {
    static Delegat delegat;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league);

        Intent intent =  getIntent();
         delegat = (Delegat) intent.getSerializableExtra("delegat");

            //Uzima listu naziva liga na kojima je delegat delegiran
            List <String>list = delegat.getLeagues() ;

            ListView leagueListView = (ListView) findViewById(R.id.leagueListView);
            //Kreira adapter pomocu kojeg popunjava listu, i setuje da svako drugo polje bude razlicite boje
            ArrayAdapter <String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,list){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
// Get the current item from ListView
                    View view = super.getView(position,convertView,parent);
                    if(position %2 == 1)
                    {
// Set a background color for ListView regular row/item
                        view.setBackgroundColor(Color.parseColor("#e6e6e6"));
                    }
                    else
                    {
// Set the background color for alternate row/item
                        view.setBackgroundColor(Color.parseColor("#f2f2f2"));
                    }
                    return view;
                }
            };
            leagueListView.setAdapter(arrayAdapter);

            /*Listener za onClick metod liste, uzima poziciju na koju je kliknuto u listi i pokrece novu aktivnost i salje joj objekat tipa Delegat i int id koji predstavlja
            id tog delegata za tu ligu*/
            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        Intent intent = new Intent(LeagueActivity.this,MatchesActivity.class);
                        intent.putExtra("delegat",delegat);
                        intent.putExtra("idDelegata",delegat.getIds().get(position));
                        startActivity(intent);


                }
            };
            leagueListView.setOnItemClickListener(itemClickListener);





    }


}
