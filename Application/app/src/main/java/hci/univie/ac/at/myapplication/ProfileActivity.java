package hci.univie.ac.at.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Benne on 01.05.2018.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvun;
    TextView tvlt;
    TextView tvks;
    TableLayout bills;
    TableRow tmrow1;
    Button btn_eintrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Mein Profil");

        tvun = (TextView) findViewById(R.id.tvShowUN);
        tvlt = (TextView) findViewById(R.id.tvShowLT);
        tvks = (TextView) findViewById(R.id.tvShowKS);
        tvun.setTextColor(Color.rgb(26, 117, 255));
        tvlt.setTextColor(Color.rgb(26, 117, 255));
        tvun.setText(Data.getInstance().getUsername().toString());
        tvlt.setText(String.valueOf(Data.getInstance().getLimit()));
        String memberName = Data.getInstance().getUsername();
        btn_eintrag = (Button)findViewById(R.id.btn_newausgabe);
        btn_eintrag.setOnClickListener(this);

        //Kontostand Berechnung

        double summ = 0;

        for (int i = 0; i < Data.getInstance().getGruppeArray().size(); ++i) {
            double val = 0;

            for (Zahlung z : Data.getInstance().getGruppeArray().get(i).getBills()) {
                if (z.getPayer().equals(memberName) || z.getPayed().contains(memberName)) {
                    int totalMembers = z.getPayed().size();
                    if (z.getPayer().equals(memberName)) {
                        val += z.getPrice();
                    }
                    if(z.getPayed().contains(memberName))val -= z.getPrice() / (double) totalMembers;
                }
            }
            summ += val;
        }
        String tempsumm = (String.format("%.2f", summ));
        if (summ >= 0) {

            tvks.setTextColor(Color.rgb(37, 142, 37));
            tvks.setText(tempsumm);
        } else {

            tvks.setTextColor(Color.rgb(255, 0, 0));
            tvks.setText(tempsumm);
        }

        //Eigene Ausgaben

        bills = (TableLayout) findViewById(R.id.tbl_Ausgabe);
        bills.removeAllViews();

        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        lp.setMargins(5, 5, 5, 5);

        for (int i = 0; i < Data.getInstance().getNutzer().getAusgaben().size(); ++i) {


            if (i == 0) {

                tmrow1 = new TableRow(this);
                Button btn = new Button(this);
                Button btn1 = new Button(this);
                Button btn2 = new Button(this);
                Button btn3 = new Button(this);

                btn.setText("Beschreibung");
                btn.setBackgroundColor(Color.WHITE);
                btn.setTextColor(Color.RED);
                btn.setTextSize(12);
                btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                btn1.setText("Summe");
                btn1.setBackgroundColor(Color.WHITE);
                btn1.setTextColor(Color.RED);
                btn1.setTextSize(12);
                btn1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


                btn3.setText("Datum");
                btn3.setBackgroundColor(Color.WHITE);
                btn3.setTextSize(12);
                btn3.setTextColor(Color.RED);
                btn3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                tmrow1.setId(i + 1);
                tmrow1.addView(btn);
                tmrow1.addView(btn1);
                tmrow1.addView(btn3);

                btn.setLayoutParams(lp);
                btn1.setLayoutParams(lp);
                btn3.setLayoutParams(lp);

                bills.addView(tmrow1, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                bills.setColumnStretchable(0, true);
                bills.setColumnStretchable(1, true);
                bills.setColumnStretchable(2, true);


            }

            tmrow1 = new TableRow(this);
            Button btn = new Button(this);
            Button btn1 = new Button(this);
            Button btn2 = new Button(this);
            Button btn3 = new Button(this);

            btn.setText(Data.getInstance().getNutzer().getAusgaben().get(i).getDescription());
            btn.setBackgroundColor(Color.WHITE);
            btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btn.setTextSize(12);

            btn1.setText(String.valueOf(Data.getInstance().getNutzer().getAusgaben().get(i).getPrice()));
            btn1.setBackgroundColor(Color.WHITE);
            btn1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btn1.setTextSize(12);


            Date tempdate = Data.getInstance().getNutzer().getAusgaben().get(i).getPaydate();
            String tempday = (String) DateFormat.format("dd", tempdate);
            String tempmonat = (String) DateFormat.format("MM", tempdate);
            String tempyear = (String) DateFormat.format("yyyy", tempdate);

            btn3.setText(tempday + "." + tempmonat + "." + tempyear);
            btn3.setBackgroundColor(Color.WHITE);
            btn3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btn3.setTextSize(12);

            tmrow1.addView(btn);
            tmrow1.addView(btn1);

            tmrow1.addView(btn3);


            btn.setLayoutParams(lp);
            btn1.setLayoutParams(lp);

            btn3.setLayoutParams(lp);

            bills.addView(tmrow1, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            bills.setColumnStretchable(0, true);
            bills.setColumnStretchable(1, true);
            bills.setColumnStretchable(2, true);


        }
    }
    //Menue
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.GRuebersicht:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.HELP:
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent1 = new Intent(this, EintragProfilActivity.class);
        intent1.putExtra("Uniqid","From_Profile_Activity");
        startActivity(intent1);
    }
}
