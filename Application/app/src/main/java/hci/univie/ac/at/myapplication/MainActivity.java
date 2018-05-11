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
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import static android.widget.Button.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    //Static variables to use in all Activities

    private static Data mainData = null;
    public static Gruppe mainGruppe;// = new Gruppe();
    Button btn_group;
    TextView tvun;
    TextView tvlt;
    TextView tvks;


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

       //Log.i("APPDIR",this.getFilesDir().getAbsolutePath());
    }

    private void createView(){
        LinearLayout myLayout = (LinearLayout) findViewById(R.id.my_groups);
        myLayout.removeAllViews();
        btn_group = (Button)findViewById(R.id.new_group_button);


        tvun = (TextView)findViewById(R.id.tvShowUNM);
        tvlt = (TextView)findViewById(R.id.tvShowLTM);
        tvks = (TextView)findViewById(R.id.tvShowKSM);
        tvun.setTextColor(Color.rgb(26, 117, 255));
        tvlt.setTextColor(Color.rgb(26, 117, 255));
        tvun.setText(mainData.getUsername().toString());
        tvlt.setText(String.valueOf(mainData.getLimit()));

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
            double val=0;

            for(Zahlung z: mainData.getGruppeArray().get(i).getBills()){
                if(z.getPayer().equals(memberName) || z.getPayed().contains(memberName)){
                    int totalMembers = z.getPayed().size();
                    if(z.getPayer().equals(memberName)){
                        val += z.getPrice();
                    }
                    val -= z.getPrice()/(double) totalMembers;
                }
            }
            String tempval = (String.format("%.2f", val));
            if(val >= 0){

                btn.setTextColor(Color.rgb(37, 142, 37));
                btn.setText(mainData.getGruppeArray().get(i).getName() + ":          + " + tempval);
            }else{

                btn.setTextColor(Color.rgb(255, 0, 0));
                btn.setText(mainData.getGruppeArray().get(i).getName() + ":           " + tempval);
            }


            btn.setId(i);
            Drawable d = getResources().getDrawable(R.drawable.button_border);
            btn.setBackground(d);
            btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


            btn.setOnClickListener((View.OnClickListener) this);
            myLayout.addView(btn, myParams);
            summ += val;
        }

        //Kontostand Berechnung

        String tempsumm = (String.format("%.2f", summ));
        if(summ >= 0){

            tvks.setTextColor(Color.rgb(37, 142, 37));
            tvks.setText(tempsumm);
        }else{

            tvks.setTextColor(Color.rgb(255, 0, 0));
            tvks.setText(tempsumm);
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
}
