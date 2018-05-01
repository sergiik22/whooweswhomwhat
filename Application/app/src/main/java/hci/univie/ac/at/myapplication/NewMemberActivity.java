package hci.univie.ac.at.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Benne on 01.05.2018.
 */

public class NewMemberActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmember);
        setTitle("Teilnehmer hinzufügen");
    }
}
