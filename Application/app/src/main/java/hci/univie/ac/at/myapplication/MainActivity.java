package hci.univie.ac.at.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;

import java.io.File;
import java.util.ArrayList;

import static android.widget.Button.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    //Static variables to use in all Activities

    private static Data mainData = null;
    public static Gruppe mainGruppe;// = new Gruppe();
    Button btn_group;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Startseite");

        if(mainData == null){
            mainData = Data.getInstance();
            mainData.readFile(this);
        }
        createView();

        Log.i("APPDIR",this.getFilesDir().getAbsolutePath());
    }

    private void createView(){
        LinearLayout myLayout = (LinearLayout) findViewById(R.id.my_groups);
        myLayout.removeAllViews();
        btn_group = (Button)findViewById(R.id.new_group_button);


        //Making new Buttons for each Group
        for (int i = 0; i < mainData.getGruppeArray().size(); ++i){
            Button btn = new Button (MainActivity.this);
            LinearLayout.LayoutParams myParams = new LinearLayout.LayoutParams (
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            myParams.setMargins(5, 10, 5, 10);
            btn.setText(mainData.getGruppeArray().get(i).getName());
            btn.setId(i);
            Drawable d = getResources().getDrawable(R.drawable.button_border);
            btn.setBackground(d);
            btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


            btn.setOnClickListener((View.OnClickListener) this);
            myLayout.addView(btn, myParams);

        }
        btn_group.setOnClickListener(this);
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
        switch (item.getItemId()) {
            case R.id.PRuebersicht:
                mainData.writeSaveFile(this);
                //open Profile View
                return true;
            case R.id.settings:
                //open Settings
                return true;
            case R.id.HELP:
                //open Help
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
            String btnName = ((Button) v).getText().toString();
            mainGruppe = mainData.getGroup(btnName);

            startActivity(intent);
        }
    }
}
