package hci.univie.ac.at.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Date;

/**
 * Created by Benne on 01.05.2018.
 */

public class HistoryActivity extends AppCompatActivity implements View.OnLongClickListener {

    TableLayout bills;
    TableRow tmrow1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Zahlungshistorie");
        bills = (TableLayout)findViewById(R.id.tbl_gesamt_historie);
        bills.removeAllViews();

        TableRow.LayoutParams lp= new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        lp.setMargins(5, 5, 5, 5);
        //Create a Row for each bill in the history
        for (int i = 0; i < MainActivity.mainGruppe.getBills().size(); ++i){


            if (i == 0){

                tmrow1 = new TableRow(this);
                Button btn = new Button (this);
                Button btn1 = new Button (this);
                Button btn2 = new Button (this);
                Button btn3 = new Button (this);

                btn.setText("Beschreibung");
                btn.setBackgroundColor(Color.WHITE);
                btn.setTextColor(Color.RED);
                btn.setTextSize(10);
                btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                btn1.setText("Summe");
                btn1.setBackgroundColor(Color.WHITE);
                btn1.setTextColor(Color.RED);
                btn1.setTextSize(10);
                btn1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                btn2.setText("Bezahlt von");
                btn2.setBackgroundColor(Color.WHITE);
                btn2.setTextColor(Color.RED);
                btn2.setTextSize(10);
                btn2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                btn3.setText("Datum");
                btn3.setBackgroundColor(Color.WHITE);
                btn3.setTextSize(10);
                btn3.setTextColor(Color.RED);
                btn3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                tmrow1.setId(i+1);
                tmrow1.addView(btn);
                tmrow1.addView(btn1);
                tmrow1.addView(btn2);
                tmrow1.addView(btn3);

                btn.setLayoutParams(lp);
                btn1.setLayoutParams(lp);
                btn2.setLayoutParams(lp);
                btn3.setLayoutParams(lp);

                bills.addView(tmrow1, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                bills.setColumnStretchable(0, true);
                bills.setColumnStretchable(1, true);
                bills.setColumnStretchable(2, true);
                bills.setColumnStretchable(3, true);

            }

            tmrow1 = new TableRow(this);
            Button btn = new Button (this);
            btn.setOnLongClickListener(this);
            Button btn1 = new Button (this);
            Button btn2 = new Button (this);
            Button btn3 = new Button (this);

            btn.setText(MainActivity.mainGruppe.getBills().get(i).getDescription());
            btn.setBackgroundColor(Color.WHITE);
            btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btn.setTextSize(10);

            btn1.setText(String.valueOf(MainActivity.mainGruppe.getBills().get(i).getPrice()));
            btn1.setBackgroundColor(Color.WHITE);
            btn1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btn1.setTextSize(10);

            btn2.setText(MainActivity.mainGruppe.getBills().get(i).getPayer());
            btn2.setBackgroundColor(Color.WHITE);
            btn2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btn2.setTextSize(10);

            Date tempdate = MainActivity.mainGruppe.getBills().get(i).getPaydate();
            String tempday = (String) DateFormat.format("dd",   tempdate);
            String tempmonat = (String) DateFormat.format("MM",   tempdate);
            String tempyear = (String) DateFormat.format("yyyy",   tempdate);

            btn3.setText(tempday + "." + tempmonat + "." + tempyear);
            btn3.setBackgroundColor(Color.WHITE);
            btn3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btn3.setTextSize(10);

            tmrow1.addView(btn);
            tmrow1.addView(btn1);
            tmrow1.addView(btn2);
            tmrow1.addView(btn3);


            btn.setLayoutParams(lp);
            btn1.setLayoutParams(lp);
            btn2.setLayoutParams(lp);
            btn3.setLayoutParams(lp);

            bills.addView(tmrow1, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            bills.setColumnStretchable(0, true);
            bills.setColumnStretchable(1, true);
            bills.setColumnStretchable(2, true);
            bills.setColumnStretchable(3, true);

        }
    }

    //Function for Return Arrow
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==android.R.id.home) this.finish();
        Intent intent1 = new Intent(this, SelectedGroupActivity.class);

        startActivity(intent1);
        return true;
    }

    /**
     * Called when a view has been clicked and held.
     *
     * @param v The view that was clicked and held.
     * @return true if the callback consumed the long click, false otherwise.
     */
    @Override
    //Löschen
    //Speichern von Zahlungen
    public boolean onLongClick(View v) {
        final String temp_name = ((Button) v).getText().toString();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(HistoryActivity.this);
        mBuilder.setIcon(android.R.drawable.sym_def_app_icon);
        mBuilder.setTitle("Zahlung loeschen");
        mBuilder.setMessage("Möchten Sie wirklich Zahlung loeschen?");

        mBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < MainActivity.mainGruppe.getBills().size(); ++i) {
                    if (MainActivity.mainGruppe.getBills().get(i).getDescription().equals(temp_name)) {
                        MainActivity.mainGruppe.getBills().remove(i);
                        saveData();
                    }

                }
            }
        });

        mBuilder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog aldialog = mBuilder.create();
        aldialog.show();

        return false;
    }
    //Speichern und Reload Activity
    public void saveData(){
        Data.getInstance().writeSaveFile(this);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);

    }
}
