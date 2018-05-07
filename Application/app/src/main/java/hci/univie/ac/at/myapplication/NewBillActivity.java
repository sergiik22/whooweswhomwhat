package hci.univie.ac.at.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbill);
        setTitle("Neue Zahlung");
        et1 = (EditText)findViewById(R.id.bill_description);
        et2 = (EditText)findViewById(R.id.bill_amount);
        sp1 = (Spinner)findViewById(R.id.member_spinner);

        //ArrayList<String> spinner_array = MainActivity.mainGruppe.getMembers();
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                new ArrayList<String>(MainActivity.mainGruppe.getMembers()));
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp1.setAdapter(spinner_adapter);
        btn_foto = (Button)findViewById(R.id.add_bill_picture);
        btn_create = (Button)findViewById(R.id.create_bill);
        btn_foto.setOnClickListener(this);
        btn_create.setOnClickListener(this);


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
            } else {
                String spinner_item = sp1.getSelectedItem().toString();
                Date currentTime = Calendar.getInstance().getTime();
                double betragnew = Double.parseDouble(getbetrag);
                Zahlung z1 = new Zahlung(getbeschreibung, betragnew, spinner_item, currentTime);
                MainActivity.mainGruppe.addBill(z1);
                Intent intent = new Intent(this, SelectedGroupActivity.class);
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
