package hci.univie.ac.at.myapplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.LoggingPermission;

/**
 * Created by Benne on 04.05.2018.
 */

enum LoopZeit{DAY,WEEK,MONTH,YEAR}

public class Zahlung {
    private String beschreibung;
    private double preis;
    private String gezahltVon;
    private ArrayList<String> gezahltAn;
    private boolean dauerAuftrag;
    private LoopZeit loopZeit;
    private Date datum;

    public Zahlung(){
        gezahltAn = new ArrayList<String>();
    }

    public void setDescription(String newDescription){
        beschreibung = newDescription;
    }
    public void setPrice(double price){
        preis = price;
    }
    public void setPayer(String name){
        gezahltVon = name;
    }
    public void setPayed(ArrayList<String> names){
        gezahltAn = names;
    }
    public void setLoop(boolean val){
        dauerAuftrag = val;
    }
    public void setInterval(String loop){
        //toDo
    }
    public void setPaydate(Date date){
        datum = date;
    }

    public String getDescription(){
        return beschreibung;
    }
    public double getPrice(){
        return preis;
    }
    public String getPayer(){
        return gezahltVon;
    }
    public ArrayList<String> getPayed(){
        return gezahltAn;
    }
    public boolean getLooped(){
        return dauerAuftrag;
    }
    public LoopZeit getLoopInterval(){
        return loopZeit;
    }
    public Date getPaydate(){
        return datum;
    }

    @Override
    public String toString(){
        String s="\n{\n"+beschreibung;
        s+="\nPreis: "+preis;
        s+="\nDatum:"+datum.toString();
        return s;
    }
}
