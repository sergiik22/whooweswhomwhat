package hci.univie.ac.at.myapplication;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    public void readFile(Activity caller){
        if(gruppen == null) gruppen = new ArrayList<Gruppe>();
        try {
            InputStream is = caller.getApplicationContext().getAssets().open(filename);
            int jsonLength = is.available();
            byte[] buff = new byte[jsonLength];
            is.read(buff);
            is.close();
            String wholeJSON = new String(buff, "UTF-8");       //Liest alle Gruppen, erste Gruppe ist Profil
            JSONArray myJSONArray = new JSONArray(wholeJSON);
            for(int i=0; i< myJSONArray.length(); i++){                     //Pro Gruppe ein Gruppenobjekt in eine Arraylist schreiben.
                JSONObject obj = myJSONArray.getJSONObject(i);
                Gruppe grp = new Gruppe();
                grp.setName(obj.getString("name"));
                JSONArray members = obj.getJSONArray("members");
                for(int j=0; j<members.length(); j++){
                    JSONObject member = members.getJSONObject(j);
                    grp.addMember(member.getString("name"));
                }
                JSONArray bills = obj.getJSONArray("bills");
                for(int j=0; j<bills.length(); j++){
                    JSONObject bill = bills.getJSONObject(j);
                    Zahlung z = new Zahlung();
                    z.setDescription(bill.getString("description"));
                    z.setPrice(bill.getDouble("amount"));
                    z.setInterval(bill.getString("loopTime"));
                    z.setLoop(bill.getBoolean("infinite"));
                    z.setPayer(bill.getString("from"));

                    ArrayList<String> names = new ArrayList<String>();
                    JSONArray payedFor = bill.getJSONArray("to");
                    for(int k=0; k<payedFor.length(); k++){
                        JSONObject person = payedFor.getJSONObject(k);
                        names.add(person.getString("name"));
                    }
                    z.setPayed(names);

                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date d = format.parse(bill.getString("payedOn"));
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d);
                    cal.set(Calendar.HOUR,0);
                    cal.set(Calendar.HOUR_OF_DAY,0);
                    cal.set(Calendar.MINUTE,0);
                    cal.set(Calendar.SECOND,0);
                    d = cal.getTime();
                    z.setPaydate(d);
                    grp.addBill(z);
                }
                gruppen.add(grp);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i("ERROR", e.getMessage());
        }
        for (Gruppe g : gruppen){
            Log.i("GRUPPE", g.toString());
        }
    }
    public void writeSaveFile(){

    }


}