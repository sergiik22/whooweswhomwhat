package hci.univie.ac.at.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Benne on 01.05.2018.
 */

public class NewMemberActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv1;
    Button btn_add;
    EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmember);
        setTitle("Teilnehmer hinzufügen");

        tv1 = (TextView)findViewById(R.id.add_member_text);
        tv1.setText("Neuer Teilnehmer bei " + MainActivity.mainGruppe.getName() + " hinzufügen");

        btn_add = (Button)findViewById(R.id.add_button);

        et1 = (EditText)findViewById(R.id.new_member_name);
        btn_add.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        String getname = et1.getText().toString();

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
        else {
            MainActivity.mainGruppe.addMember(getname);
            Intent intent = new Intent(this, SelectedGroupActivity.class);
            startActivity(intent);

        }
    }
}
