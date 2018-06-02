package hci.univie.ac.at.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.io.File;
import java.util.ArrayList;

import static android.widget.Button.*;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnLongClickListener {
    //Static variables to use in all Activities

    private static Data mainData = null;
    public static Gruppe mainGruppe;// = new Gruppe();
    Button btn_group;
    TextView tvun;
    TextView tvlt;
    TextView tvks;


    //Mainscreen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Gruppenübersicht");

        if(mainData == null){
            mainData = Data.getInstance();
            mainData.readFile(this);
        }
        createView();
    }

    //Create all viewElements
    private void createView(){
        LinearLayout myLayout = (LinearLayout) findViewById(R.id.my_groups);
        myLayout.removeAllViews();
        btn_group = (Button)findViewById(R.id.new_group_button);


        tvun = (TextView)findViewById(R.id.tvShowUNM);
        tvun.setOnLongClickListener(this);
        tvlt = (TextView)findViewById(R.id.tvShowLTM);
        tvlt.setOnLongClickListener(this);
        tvks = (TextView)findViewById(R.id.tvShowKSM);
        tvun.setTextColor(Color.rgb(26, 117, 255));
        tvun.setText(mainData.getUsername().toString());


        double summ = 0;

        //Making new Buttons for each Group
        for (int i = 0; i < mainData.getGruppeArray().size(); ++i){
            Button btn = new Button (MainActivity.this);
            LinearLayout.LayoutParams myParams = new LinearLayout.LayoutParams (
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            myParams.setMargins(5, 10, 5, 10);

            String memberName = mainData.getUsername();
            double val = 0;

            for(Zahlung z: mainData.getGruppeArray().get(i).getBills()){
                if(z.getPayer().equals(memberName) || z.getPayed().contains(memberName)){
                    int totalMembers = z.getPayed().size();
                    if(z.getPayer().equals(memberName)){
                        val += z.getPrice();
                    }
                    if(z.getPayed().contains(memberName))val -= z.getPrice()/(double) totalMembers;
                }
            }
            summ += val;

            //Color green or red based on value
            if(val >= 0){

                btn.setTextColor(Color.rgb(37, 142, 37));

            }else{

                btn.setTextColor(Color.rgb(255, 0, 0));
                val = val * -1;
            }
            String tempval = (String.format("%.2f", val));
            btn.setText(mainData.getGruppeArray().get(i).getName() + ":          " + tempval);



            btn.setId(i);
            Drawable d = getResources().getDrawable(R.drawable.button_border);
            btn.setBackground(d);
            btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


            btn.setOnClickListener((View.OnClickListener) this);
            btn.setOnLongClickListener(this);
            myLayout.addView(btn, myParams);
        }

        //User finance state calculation

        String tempsumm = (String.format("%.2f", summ));
        if(summ >= 0){

            tvks.setTextColor(Color.rgb(37, 142, 37));
            tvks.setText(tempsumm);
            tvlt.setTextColor(Color.rgb(37, 142, 37));
            tvlt.setText(String.format("%.2f",mainData.getLimit()));
        }else{

            tvks.setTextColor(Color.rgb(255, 0, 0));
            tvks.setText(tempsumm);
            if (summ * (-1) > mainData.getLimit()){
                tvlt.setTextColor(Color.rgb(255, 0, 0));
                tvlt.setText(String.format("%.2f",mainData.getLimit()));
            }
            else {
                tvlt.setTextColor(Color.rgb(37, 142, 37));
                tvlt.setText(String.format("%.2f",mainData.getLimit()));
            }
        }

        btn_group.setOnClickListener(this);

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
            case R.id.PRuebersicht:
                intent = new Intent(this, ProfileActivity.class);
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
    @Override
    public void onClick(View v) {
        int tempid = v.getId();
        if (tempid == R.id.new_group_button){
            Intent intent = new Intent(this, NewGroupActivity.class);
            startActivity(intent);
        }
        //On click start activity SelectedGroupActivity. mainGruppe is now the chosen group
        else {
            Intent intent = new Intent(this, SelectedGroupActivity.class);
            String temp_string = ((Button) v).getText().toString();
            String[] btnName = temp_string.split(":");
            mainGruppe = mainData.getGroup(btnName[0]);

            startActivity(intent);
        }
    }

    /**
     * Called when a view has been clicked and held.
     *
     * @param v The view that was clicked and held.
     * @return true if the callback consumed the long click, false otherwise.
     */
    @Override
    //Löschen von Gruppen
    public boolean onLongClick(View v) {

        int tempid = v.getId();
        if (tempid == R.id.tvShowUNM) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Username aendern ");
            final EditText input = new EditText(this);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                 
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Name kann nicht leer sein",
                                    Toast.LENGTH_LONG).show();
                            tvun.performLongClick();
                        }
                        else
                        {

                        mainData.setNewUsername(input.getText().toString());
                        saveData();
                    }

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }

        else if (tempid == R.id.tvShowLTM){


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Limit aendern ");
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Limit kann nicht leer sein",
                                    Toast.LENGTH_LONG).show();
                            tvlt.performLongClick();
                        }
                            else
                        {
                            mainData.setNewLimit(Double.parseDouble(input.getText().toString()));
                            saveData();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }

        else {

            final Intent intent = new Intent(this, MainActivity.class);
            String temp_string = ((Button) v).getText().toString();
            final String[] btnName = temp_string.split(":");
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            mBuilder.setIcon(android.R.drawable.sym_def_app_icon);
            mBuilder.setTitle("Gruppe loeschen");
            mBuilder.setMessage("Möchten Sie wirklich die Gruppe \"" + btnName[0] + "\" loeschen?");
            final boolean[] flag = {false};

            mBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < mainData.getGruppeArray().size(); ++i) {
                        if (mainData.getGruppeArray().get(i).getName().equals(btnName[0])) {
                            mainData.getGruppeArray().remove(i);
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
        }
        return false;
    }
    //Speichern und reload Activity
    public void saveData(){
        mainData.writeSaveFile(this);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);


    }
}
