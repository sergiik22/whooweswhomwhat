package hci.univie.ac.at.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    //Static variables to use in all Activities

    public static Data mainData = new Data();
    public static Gruppe mainGruppe;// = new Gruppe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Startseite");
        LinearLayout myLayout = (LinearLayout) findViewById(R.id.my_groups);
        mainData.readFile(this);

        //Making new Buttons for each Group
        for (int i = 0; i < mainData.getGruppeArray().size(); ++i){
            Button btn = new Button (MainActivity.this);
            LinearLayout.LayoutParams myParams = new LinearLayout.LayoutParams (
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            btn.setText(mainData.getGruppeArray().get(i).getName());
            btn.setId(i);
            Drawable d = getResources().getDrawable(R.drawable.button_border);
            btn.setBackground(d);
            btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            btn.setOnClickListener((View.OnClickListener) this);
            myLayout.addView(btn, myParams);

        }

    }
    @Override
    public void onClick(View v) {

        //On click start activity SelectedGroupActivity. mainGruppe is now the chosen group

        Intent intent = new Intent(this, SelectedGroupActivity.class);
        String btnName = ((Button)v).getText().toString();
        mainGruppe = mainData.getGroup(btnName);
        Log.i("MainGruppe", mainGruppe.getName());

        startActivity(intent);
    }
}
