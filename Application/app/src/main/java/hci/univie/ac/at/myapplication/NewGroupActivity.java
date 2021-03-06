package hci.univie.ac.at.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Benne on 01.05.2018.
 */

public class NewGroupActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher, View.OnKeyListener {

    private Data data = null;
    EditText et1;
    EditText et2;
    Button btn_add;
    Button btn_create;
    TableLayout tableMembers;
    int i = 0;
    TableRow tmrow;
    TextView tv;
    Drawable d;
    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT);
    ArrayList<String> names = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(data == null) data = Data.getInstance();
        setContentView(R.layout.activity_newgroup);
        setTitle("Gruppe erstellen");
        d = getResources().getDrawable(R.drawable.button_border);
        et1 = (EditText) findViewById(R.id.group_name);
        et2 = (EditText) findViewById(R.id.new_member_name);
        et2.addTextChangedListener(this);

        btn_add = (Button) findViewById(R.id.add_new_member);
        btn_create = (Button) findViewById(R.id.create_button);
        tableMembers = (TableLayout) findViewById(R.id.new_members_table);

        lp.setMargins(5, 5, 5, 5);

        //User is in Group by default

        tmrow = new TableRow(this);
        tv = new TextView(this);
        tv.setBackground(d);
        tv.setHeight(130);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        tv.setTextSize(18);
        tv.setTextColor(Color.rgb(0, 0, 0));
        tv.setText("  " + data.getUsername());
        tv.setId(i);
        tmrow.setId(i);
        tmrow.addView(tv);
        tv.setLayoutParams(lp);
        tableMembers.addView(tmrow);
        tableMembers.setColumnStretchable(0, true);
        ++i;
        names.add(data.getUsername());

        btn_add.setOnClickListener(this);
        btn_create.setOnClickListener(this);


        et1.setOnKeyListener(this);


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
        String getname = et2.getText().toString();
        String getname1 = et1.getText().toString();
        int tempid = v.getId();
        if (tempid == R.id.add_new_member) {
            //Error
            if (getname.matches("")) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewGroupActivity.this);
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
            //Add
            else {
                lp.setMargins(5, 5, 5, 5);
                tmrow = new TableRow(this);
                tv = new TextView(this);
                tv.setBackground(d);
                tv.setHeight(130);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                tv.setTextSize(18);
                tv.setTextColor(Color.rgb(0, 0, 0));
                tv.setText("  " + getname);
                tv.setId(i);
                tmrow.setId(i);
                tmrow.addView(tv);
                tv.setLayoutParams(lp);
                tableMembers.addView(tmrow);
                tableMembers.setColumnStretchable(0, true);
                ++i;
                names.add(getname);
                et2.getText().clear();


                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
        }
        else if (tempid == R.id.create_button){
            //Error
            if (getname1.matches("")) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewGroupActivity.this);
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
            //Add
            else {

                Gruppe grnew = new Gruppe(getname1, names);
                data.addGruppe(grnew);
                data.writeSaveFile(this);
                Intent intent1 = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent1);
            }
        }
    }


    //Check if name already used
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        return;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(names.contains(s.toString().trim())){
            et2.setError("Keine doppelten Namen!");
            btn_add.setEnabled(false);
        }else {
            btn_add.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        return;
    }


    //Add on enter?
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        {
            if ( (event.getAction() == KeyEvent.ACTION_DOWN  ) &&
                    (keyCode           == KeyEvent.KEYCODE_ENTER)   )
            {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et1.getWindowToken(), 0);
                return true;
            }
            return false;


    }
}
}