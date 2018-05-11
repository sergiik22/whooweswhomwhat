package hci.univie.ac.at.myapplication;

import java.util.ArrayList;

/**
 * Created by Benne on 09.05.2018.
 */

public class Profil {

    private String benutzerName;
    private ArrayList<Zahlung> ausgaben;
    private double limit;

    //Constructor
    public Profil(){
        ausgaben = new ArrayList<Zahlung>();
    }

    public Profil(String name, double amount){
        benutzerName = name;
        limit = amount;
        ausgaben = new ArrayList<Zahlung>();
    }
    //Setter
    public void setBenutzerName(String name){
        benutzerName = name;
    }
    public void addAusgabe(Zahlung a){
        ausgaben.add(a);
    }
    public void setLimit(double newLimit){
        limit = newLimit;
    }
    //Getter
    public String getBenutzerName(){
        return benutzerName;
    }
    public double getLimit(){
        return limit;
    }
    public ArrayList<Zahlung> getAusgaben(){
        return ausgaben;
    }

    @Override
    public String toString(){
        String s ="";
        s+="\"user\":\""+benutzerName+"\",\n";
        s+="\"limit\":"+limit+",\n";
        s+="\"bills\":[\n";
        for(int i=0; i<ausgaben.size(); i++){
            if(i!=0)s+=",\n";
            s+=ausgaben.get(i).profileOBJString();
        }
        s+="],\n";
        return s;
    }

}
