package com.dscore.mihailo.dscoref;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import model.Delegat;
import model.Match;


public class MatchesActivity extends AppCompatActivity {
    static Delegat delegat;
    static int idDelegata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_matches);

            //Preuzima objekte iz prethodne aktivnosti
            Intent intent = getIntent();
             delegat = (Delegat) intent.getSerializableExtra("delegat");
            idDelegata= intent.getIntExtra("idDelegata", 0);

            //Preuzima listu utakmica iz odabrane lige na kojima je korisnik delegat
            List<Match> list=new ArrayList<>();
            try {
                 list = (List<Match>) new Match().execute("1",String.valueOf(idDelegata)).get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            //Popunjava liste sa informacijama  koje ce biti prosledjene adapteru da se ubace u listView
            List<String> list1 = new ArrayList<>();
            List<String> list2 = new ArrayList<>();
            for (int i=0;i<list.size();i++){
                Match match = list.get(i);
                //Formatiranje Stringa time
                try {
                    SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = sdfSource.parse(match.getTime());
                    SimpleDateFormat sdfDestination = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
                    match.setTime(sdfDestination.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String name = new String("Vreme: "+match.getTime()+"\n"+"Domaci: "+match.getFirstTeam()+"\n"+"Gosti: "+match.getSecondTeam());
                String result=null;
                if(match.getResult().contentEquals("Utakmica nije odigrana")){
                    result=new String("\n"+"Utakmica nije odigrana"+"\n");
                }else {
                    result = new String("\n"+"Rezultat: "+match.getResult()+"\n");
                }

                list1.add(name);
                list2.add(result);
            }
            ListView matchesListView = (ListView) findViewById(R.id.listViewMatches);

            //Setovanje adaptera sa listom stringova sa nazivom timova i vremenom, kao i sa custom layout-om za listView i razlicitom bojom za svaki drugi item liste
            final ArrayAdapter <String> arrayAdapter = new ArrayAdapter(this, R.layout.my_matches_list_view,list1){
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
            matchesListView.setAdapter(arrayAdapter);


//Setovanje adaptera sa listom stringova sa rezultatom, kao i sa custom layout-om za listView i razlicitom bojom za svaki drugi item liste
            ListView resultsListView = (ListView) findViewById(R.id.listViewResults);
            final ArrayAdapter <String> arrayAdapter2 = new ArrayAdapter(this, R.layout.my_matches_list_view,list2){
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
            resultsListView.setAdapter(arrayAdapter2);


            //Kreiranje onClick metoda za obe liste, kada se klikne na listu uzima i prosledjuje sledecoj aktivnosti id kliknutog meca, ime prvog tima, ime drugog tima i objekat tipa Delegat
            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Intent intent = new Intent(MatchesActivity.this,MatchActivity.class);
                    intent.putExtra("delegat",delegat);
                    intent.putExtra("idDelegata",idDelegata);
                    List<Match> list=null;
                    try {
                        list =(List<Match>) new Match().execute("1",String.valueOf(idDelegata)).get();

                        Match m= list.get(position);
                        Log.i("pozicija",m.getFirstTeam());
                        intent.putExtra("id",m.getId());
                        intent.putExtra("firstTeam",m.getFirstTeam());
                        intent.putExtra("secondTeam",m.getSecondTeam());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);


                }
            };
            //Dodeljivanje prvoj listi slusaca
            matchesListView.setOnItemClickListener(itemClickListener);
            //Dodeljivanje drugoj listi slusaca
            resultsListView.setOnItemClickListener(itemClickListener);


        }

}
