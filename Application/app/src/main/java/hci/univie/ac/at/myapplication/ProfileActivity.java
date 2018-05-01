package hci.univie.ac.at.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Benne on 01.05.2018.
 */

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profil");
    }
}
