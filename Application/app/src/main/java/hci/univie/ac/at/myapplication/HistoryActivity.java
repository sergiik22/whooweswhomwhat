package hci.univie.ac.at.myapplication;

import android.graphics.Color;
import android.os.Bundle;
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

public class HistoryActivity extends AppCompatActivity {

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
        return super.onOptionsItemSelected(item);
    }
}
