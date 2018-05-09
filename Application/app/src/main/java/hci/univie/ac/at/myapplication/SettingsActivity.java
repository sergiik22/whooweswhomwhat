package hci.univie.ac.at.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

/**
 * Created by Benne on 09.05.2018.
 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher{

    private Set<String> allNames;
    private Data local;
    private EditText et_userName;
    private EditText et_limit;
    private Button btn_saveSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Einstellungen");

        local = Data.getInstance();
        allNames = local.getAllNames();
        allNames.remove(local.getUsername());
        et_userName = findViewById(R.id.username_field);
        et_userName.setHint(local.getUsername());

        et_userName.addTextChangedListener(this);

        et_limit = findViewById(R.id.user_limit);
        et_limit.setHint(String.valueOf(local.getLimit()));
        btn_saveSettings = findViewById(R.id.saveSettings);

        btn_saveSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(et_userName.getText().toString().equals("") && et_limit.getText().toString().equals("")){

            Toast.makeText(SettingsActivity.this,"Keine erlaubten Änderungen gefunden!",Toast.LENGTH_LONG).show();

        }else{
        if(!et_userName.getText().toString().equals("")){
            String newUser = et_userName.getText().toString();
            local.setNewUsername(et_userName.getText().toString());
        }
        if(!et_limit.getText().toString().equals("")){
            double newLimit = Double.parseDouble(et_limit.getText().toString());
            local.setNewLimit(Double.parseDouble(et_limit.getText().toString()));
        }
        local.writeSaveFile(this);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        return;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(allNames.contains(s.toString().trim())){
            et_userName.setError("Name already used");
            btn_saveSettings.setEnabled(false);
        }
        else{
            btn_saveSettings.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        return;
    }

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
            case R.id.GRuebersicht:
                intent = new Intent(this, MainActivity.class);
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
}
