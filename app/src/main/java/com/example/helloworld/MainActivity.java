package com.example.helloworld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {
    private Button buttonOk;
    private EditText editTextNumber;
    private TextView textViewIndication;

    private ProgressBar progressBarTentatives;
    private TextView textViewCounter;
    private TextView textViewScore;
    private ListView listViewHistorique;
    private int secret;
    private int counter;
    private int score;
    private List<String>  listHistorique = new ArrayList<>();
    private int maxTentatives = 7;
    private boolean bravo;
    private  ArrayAdapter<String> model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOk = findViewById(R.id.buttonOk);
        editTextNumber = findViewById(R.id.editTextNumber);
        textViewIndication = findViewById(R.id.textViewIndication);

        progressBarTentatives = findViewById(R.id.progressBarTentatives);
        textViewCounter = findViewById(R.id.textViewCounter);
        listViewHistorique = findViewById(R.id.listViewHistorique);
        textViewScore = findViewById(R.id.textViewScore);

        model = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listHistorique);
        listViewHistorique.setAdapter(model);

        insialisation();

        buttonOk.setOnClickListener((evt)->{
            int number = parseInt(editTextNumber.getText().toString());
            if(number<secret) {
                textViewIndication.setText(R.string.val_inf);
                listHistorique.add((counter+1)+" => "+number+" "+getString(R.string.est_inf));
                model.notifyDataSetChanged();
            }
            else if(number>secret){
                textViewIndication.setText(R.string.val_sup);
                listHistorique.add((counter+1)+" => "+number+" "+getString(R.string.est_sup));
                model.notifyDataSetChanged();
            }
            else{
                textViewIndication.setText(R.string.bravo);
                score+=5;
                textViewScore.setText(String.valueOf(score));
                ganger();
                bravo = true;
            }
            ++counter;
            textViewCounter.setText(String.valueOf(counter));
            progressBarTentatives.setProgress((counter));

            if(bravo==false){
                if(counter>=maxTentatives){
                    score = 0;
                    textViewScore.setText(String.valueOf(score));
                    correctValeur();
                }
            }
            editTextNumber.setText("");
        });
    }

    private void correctValeur() {
        //Log.i("MyLog", "La bonne valeur...");
        AlertDialog alertDialogue = new AlertDialog.Builder(this).create();
        alertDialogue.setTitle(getString(R.string.bonne_valeur)+secret);
        alertDialogue.setButton(alertDialogue.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rejouer();
            }
        });
        alertDialogue.show();
    }

    private void ganger() {
        //Log.i("MyLog", "GANGER...");
        AlertDialog alertDialogue = new AlertDialog.Builder(this).create();
        alertDialogue.setTitle(getString(R.string.felicitation));
        alertDialogue.setButton(alertDialogue.BUTTON_POSITIVE, getString(R.string.gagnant), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rejouer();
            }
        });
        alertDialogue.show();
    }

    private void rejouer() {
        //Log.i("MyLog", "REJOUER...");
        AlertDialog alertDialogue = new AlertDialog.Builder(this).create();
        alertDialogue.setTitle(getString(R.string.rejouer));
        alertDialogue.setButton(alertDialogue.BUTTON_POSITIVE, getString(R.string.oui), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insialisation();
                textViewIndication.setText("");
                editTextNumber.setText("");
            }
        });

        alertDialogue.setButton(alertDialogue.BUTTON_NEGATIVE, getString(R.string.quitter), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialogue.show();
    }

    private void insialisation(){
        bravo = false;
        secret = 1+((int)(Math.random()*100));
        Log.i("MyLog","secret = "+secret);
        counter = 0;

        listHistorique.clear();
        model.notifyDataSetChanged();

        textViewCounter.setText(String.valueOf(counter));
        progressBarTentatives.setProgress(counter);
        progressBarTentatives.setMax(maxTentatives);


    }
}
