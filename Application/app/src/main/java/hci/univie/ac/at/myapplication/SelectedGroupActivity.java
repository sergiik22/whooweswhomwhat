package hci.univie.ac.at.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Benne on 01.05.2018.
 */

public class SelectedGroupActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    TableLayout tableMembers;
    TableLayout bills;
    TableRow tmrow;
    TableRow tmrow1;
    Button btn_member;
    Button btn_bill;
    Button btn_historie;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectedgroup);

        TableLayout myLayout = (TableLayout) findViewById(R.id.tblmembers);
        myLayout.removeAllViews();

        TableLayout myLayout1 = (TableLayout) findViewById(R.id.bill_history);
        myLayout1.removeAllViews();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setTitle("Gruppenübersicht");

        tableMembers = (TableLayout) findViewById(R.id.tblmembers);
        bills = (TableLayout) findViewById(R.id.bill_history);
        tableMembers.removeAllViews();
        bills.removeAllViews();
        btn_member = (Button) findViewById(R.id.add_new_member);
        btn_bill = (Button) findViewById(R.id.add_new_entry);

        btn_historie = (Button) findViewById(R.id.gesamt_historie);
        btn_historie.setOnClickListener(this);
        btn_member.setOnClickListener(this);
        btn_bill.setOnClickListener(this);

        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        lp.setMargins(5, 5, 5, 5);


        //Adding members
        for (int i = 0; i < MainActivity.mainGruppe.getMembers().size(); ++i) {


            tmrow = new TableRow(this);
            Button btn = new Button(this);
            Button btn1 = new Button(this);

            btn.setBackgroundColor(Color.WHITE);
            btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            //CALCULATION
            double calcValue = MainActivity.mainGruppe.calculateForMember(i);
            if (calcValue >= 0) {

                btn1.setTextColor(Color.rgb(37, 142, 37));
            } else {

                btn1.setTextColor(Color.rgb(255, 0, 0));
                calcValue = calcValue * -1.0;
            }


            btn.setText(MainActivity.mainGruppe.getMembers().get(i));
            btn.setId(111);
            btn.setOnLongClickListener(this);
            btn1.setText(String.format("%.2f", calcValue));
            btn1.setBackgroundColor(Color.WHITE);
            btn1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            tmrow.addView(btn);
            tmrow.addView(btn1);

            btn.setLayoutParams(lp);
            btn1.setLayoutParams(lp);

            tableMembers.addView(tmrow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    100));
            tableMembers.setColumnStretchable(0, true);
            tableMembers.setColumnStretchable(1, true);
        }
        //Adding bills
        int count = MainActivity.mainGruppe.getBills().size();
        int stop = 0;
        if (count > 3) {
            stop = count - 3;
        }
        for (int i = count - 1; i >= stop; --i) {

            // First raw description
            if (i == count - 1) {

                tmrow1 = new TableRow(this);
                Button btn = new Button(this);
                Button btn1 = new Button(this);
                Button btn2 = new Button(this);

                btn.setText("Beschreibung");


                btn.setBackgroundColor(Color.WHITE);
                btn.setTextColor(Color.BLACK);
                btn.setTypeface(Typeface.DEFAULT_BOLD);
                btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                btn1.setText("Summe");

                btn1.setBackgroundColor(Color.WHITE);
                btn1.setTextColor(Color.BLACK);
                btn1.setTypeface(Typeface.DEFAULT_BOLD);
                btn1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                btn2.setText("Bezahlt von");
                btn2.setBackgroundColor(Color.WHITE);
                btn2.setTextColor(Color.BLACK);
                btn2.setTypeface(Typeface.DEFAULT_BOLD);
                btn2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                tmrow1.setId(i+1);
                tmrow1.addView(btn);
                tmrow1.addView(btn1);
                tmrow1.addView(btn2);

                btn.setLayoutParams(lp);
                btn1.setLayoutParams(lp);
                btn2.setLayoutParams(lp);

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

            btn.setText(MainActivity.mainGruppe.getBills().get(i).getDescription());
            btn.setOnLongClickListener(this);
            btn.setId(222);
            btn.setBackgroundColor(Color.WHITE);
            btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            btn1.setText(String.format("%.2f", MainActivity.mainGruppe.getBills().get(i).getPrice()));

            btn1.setBackgroundColor(Color.WHITE);
            btn1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            btn2.setText(MainActivity.mainGruppe.getBills().get(i).getPayer());

            btn2.setBackgroundColor(Color.WHITE);
            btn2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            tmrow1.addView(btn);
            tmrow1.addView(btn1);
            tmrow1.addView(btn2);


            btn.setLayoutParams(lp);
            btn1.setLayoutParams(lp);
            btn2.setLayoutParams(lp);

            bills.addView(tmrow1, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            bills.setColumnStretchable(0, true);
            bills.setColumnStretchable(1, true);
            bills.setColumnStretchable(2, true);

        }

    }


    //Function for Return Arrow
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) this.finish();
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int tempid = v.getId();

        switch (tempid) {
            case (R.id.add_new_member):
                Intent intent = new Intent(this, NewMemberActivity.class);
                startActivity(intent);
                break;
            case (R.id.add_new_entry):
                Intent intent1 = new Intent(this, NewBillActivity.class);
                intent1.putExtra("Uniqid", "From_Selected_Group_Activity");
                startActivity(intent1);
                break;
            case (R.id.gesamt_historie):
                Intent intent2 = new Intent(this, HistoryActivity.class);
                startActivity(intent2);
                break;
        }

    }

    /**
     * Called when a view has been clicked and held.
     *
     * @param v The view that was clicked and held.
     * @return true if the callback consumed the long click, false otherwise.
     */
    //Löschen von Mitgliedern oder Zahlungen
    @Override
    public boolean onLongClick(View v) {
        int tempid = v.getId();
        final Intent intent = new Intent(this, SelectedGroupActivity.class);
        final String temp_name = ((Button) v).getText().toString();
        switch (tempid) {
        //Löschen von Mitgliedern
            case (111):

                for (int i = 0; i < MainActivity.mainGruppe.getMembers().size(); ++i) {
                    if (MainActivity.mainGruppe.getMembers().get(i).equals(temp_name)) {
                        final int res = i;
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SelectedGroupActivity.this);
                        mBuilder.setIcon(android.R.drawable.sym_def_app_icon);
                        mBuilder.setTitle("Mitglied loeschen");
                        mBuilder.setMessage("Möchten Sie wirklich \"" + temp_name + "\" aus der Gruppe loeschen?");

                        mBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Wenn Administrator verboten

                                if (temp_name.equals(Data.getInstance().getUsername())) {
                                    Toast.makeText(getApplicationContext(), "Administrator kann nicht geloescht werden",
                                            Toast.LENGTH_LONG).show();
                                }
                                //Wenn bilanz != 0 verboten
                                else if (MainActivity.mainGruppe.calculateForMember(res) != 0) {
                                    Toast.makeText(getApplicationContext(), temp_name + " kann nicht geloescht werden. Bilanz ist nicht gleich 0",
                                            Toast.LENGTH_LONG).show();
                                }
                                //Löschen, speichern und reload Activity
                                else {

                                    MainActivity.mainGruppe.getMembers().remove(res);
                                    saveData();
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


                    }
                }
                break;
            //Löschen von Zahlungen
            case (222):
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SelectedGroupActivity.this);
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

                break;

        }
        return false;
    }
    //Speichern und reload Activity
    public void saveData(){
        Data.getInstance().writeSaveFile(this);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);

    }
}

