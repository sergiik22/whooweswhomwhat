package hci.univie.ac.at.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Benne on 01.05.2018.
 */

public class NewMemberActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher {

    TextView tv1;
    Button btn_add;
    EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmember);

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Teilnehmer hinzufügen");

        tv1 = (TextView)findViewById(R.id.add_member_text);
        tv1.setText("Neuer Teilnehmer bei " + MainActivity.mainGruppe.getName() + " hinzufügen");

        btn_add = (Button)findViewById(R.id.add_button);

        et1 = (EditText)findViewById(R.id.new_member_name);
        et1.addTextChangedListener(this);
        btn_add.setOnClickListener(this);

    }

    //Function for Return Arrow
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==android.R.id.home) this.finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        String getname = et1.getText().toString();
        //ERROR
        if (getname.matches("")){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewMemberActivity.this);
            mBuilder.setIcon(android.R.drawable.sym_def_app_icon);
            mBuilder.setTitle("Falsche Name");
            mBuilder.setMessage("Name kann nicht leer sein!");
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
            MainActivity.mainGruppe.addMember(getname);
            Data.getInstance().writeSaveFile(this);
            Intent intent = new Intent(this, SelectedGroupActivity.class);
            startActivity(intent);

        }
    }

    //Check if name is already used
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        return;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(MainActivity.mainGruppe.getMembers().contains(s.toString().trim())){
            //Log.i("NAMECOMPARE",s.toString());
            et1.setError("Keine doppelten Namen!");
            btn_add.setEnabled(false);
        }else{
            btn_add.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        return;
    }
}
