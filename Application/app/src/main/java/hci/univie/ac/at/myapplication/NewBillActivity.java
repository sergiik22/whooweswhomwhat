package hci.univie.ac.at.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Benne on 01.05.2018.
 */

public class NewBillActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et1;
    EditText et2;
    Spinner sp1;
    Button btn_foto;
    Button btn_create;
    CheckBox cb;
    Spinner sp2;
    boolean is_checked = false;
    private Data local = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbill);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sp1 = (Spinner)findViewById(R.id.member_spinner);
        setTitle("Neue Zahlung");



        et1 = (EditText)findViewById(R.id.bill_description);
        et2 = (EditText)findViewById(R.id.bill_amount);

        sp2 = (Spinner)findViewById(R.id.spinner_loop);
        cb = (CheckBox)findViewById(R.id.cb_loop);
        sp2.setEnabled(false);
        local = Data.getInstance();

        //Members Spinner
                ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_item,
                        new ArrayList<String>(MainActivity.mainGruppe.getMembers()));
                spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                sp1.setAdapter(spinner_adapter);

        //Loop Spiner
        ArrayList<String> arrtemp = new ArrayList<>();
        arrtemp.add("DAY");
        arrtemp.add("WEEK");
        arrtemp.add("MONTH");
        arrtemp.add("YEAR");
        ArrayAdapter<String> spinner_adapter2 =  new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
               arrtemp);
        spinner_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(spinner_adapter2);
        sp2.setSelection(0);

        btn_foto = (Button)findViewById(R.id.add_bill_picture);
        btn_create = (Button)findViewById(R.id.create_bill);
        btn_foto.setOnClickListener(this);
        btn_create.setOnClickListener(this);
        //If CheckBox is checked Loop Spinner is active

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {sp2.setEnabled(true); is_checked = true;}
                else if (!isChecked) {sp2.setEnabled(false); is_checked = false;}
            }
        }
        );


    }
    //Function for Return Arrow
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==android.R.id.home) this.finish();
        return super.onOptionsItemSelected(item);
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        String getbeschreibung = et1.getText().toString();
        String getbetrag = et2.getText().toString();
        int tempid = v.getId();
        if (tempid == btn_create.getId()) {

            //Error
            if (getbeschreibung.matches("")) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewBillActivity.this);
                mBuilder.setIcon(android.R.drawable.sym_def_app_icon);
                mBuilder.setTitle("Falsche Beschreibung");
                mBuilder.setMessage("Beschreibung kann nicht leer sein!");
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog aldialog = mBuilder.create();
                aldialog.show();
            } else if (getbetrag.matches("")) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewBillActivity.this);
                mBuilder.setIcon(android.R.drawable.sym_def_app_icon);
                mBuilder.setTitle("Falsche Betrag");
                mBuilder.setMessage("Betrag kann nicht leer sein!");
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog aldialog = mBuilder.create();
                aldialog.show();
            }
            //ADD
            else {

                         String spinner_item = sp1.getSelectedItem().toString();
                         Date currentTime = Calendar.getInstance().getTime();
                         double betragnew = Double.parseDouble(getbetrag);
                         //If !checked without loop
                         if (!is_checked)
                         {
                            Zahlung z1 = new Zahlung(getbeschreibung, betragnew, spinner_item, currentTime);
                            ArrayList<String> names = new ArrayList<String>();
                            for(String s:MainActivity.mainGruppe.getMembers()) names.add(s);
                            z1.setPayed(names);
                            z1.setLoop(false);
                            z1.setInterval("NONE");
                             MainActivity.mainGruppe.addBill(z1);

                             }
                         //If checked with Loop, another Constructor
                          else if (is_checked)
                             {
                              String spinner_item2 = sp2.getSelectedItem().toString();
                              Zahlung z1 = new Zahlung(getbeschreibung, betragnew, spinner_item, currentTime,
                                                                                    spinner_item2);
                               ArrayList<String> names = new ArrayList<String>();
                               for(String s:MainActivity.mainGruppe.getMembers()) names.add(s);
                               z1.setPayed(names);
                               z1.setLoop(true);
                                  MainActivity.mainGruppe.addBill(z1);

                }

                Data.getInstance().writeSaveFile(this);
                Intent intent = new Intent(this, SelectedGroupActivity.class);
                finish();
                startActivity(intent);

            }
        }
        else if (tempid == btn_foto.getId()){
            //to DO maybe...
            /*Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(intent);*/
        }
    }

}
