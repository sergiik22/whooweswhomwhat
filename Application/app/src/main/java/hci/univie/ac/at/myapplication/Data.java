package hci.univie.ac.at.myapplication;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Benne on 04.05.2018.
 */

public class Data extends Application{

    private static String filename = "APP_DATA.json";
    private static Data instance = null;
    private ArrayList<Gruppe> gruppen = null;

    private Data(){}

    public static Data getInstance(){
        if(instance==null) return new Data();
        return instance;
    }

    public void readFile(){
        try {
            InputStream is = getAssets().open(filename);
            int jsonLength = is.available();
            byte[] buff = new byte[jsonLength];
            is.read(buff);
            is.close();
            String wholeJSON = new String(buff, "UTF-8");       //Liest alle Gruppen, erste Gruppe ist Profil
            JSONArray myJSONArray = new JSONArray(wholeJSON);
            for(int i=0; i< myJSONArray.length(); i++){                     //Pro Gruppe ein Gruppenobjekt in eine Arraylist schreiben.
                JSONObject obj = myJSONArray.getJSONObject(i);

            }
        }catch (Exception e){
            Log.i("ERROR", e.getMessage());
        }

    }
    public void writeSaveFile(){

    }


}