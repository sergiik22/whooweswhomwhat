package hci.univie.ac.at.myapplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.LoggingPermission;

/**
 * Created by Benne on 04.05.2018.
 */

enum LoopZeit{NONE,DAY,WEEK,MONTH,YEAR}

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

    public Zahlung (String b, double p, String gv, Date date){
        beschreibung = b;
        preis = p;
        gezahltVon = gv;
        datum = date;
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
        switch (loop){
            case "DAY":
                loopZeit = LoopZeit.DAY;
                break;
            case "WEEK":
                loopZeit = LoopZeit.WEEK;
                break;
            case "MONTH":
                loopZeit = LoopZeit.MONTH;
                break;
            case "YEAR":
                loopZeit = LoopZeit.YEAR;
                break;
            default:
            loopZeit = LoopZeit.NONE;
        }
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

    public boolean hasMember(String name){
        if(gezahltVon.equals(name))return true;
        else if(gezahltAn.contains(name)) return true;
        else return false;
    }
    public void changeName(String oldName, String newName){
        if(gezahltVon.equals(oldName))gezahltVon=newName;
        else if(gezahltAn.contains(oldName)){
            gezahltAn.remove(oldName);
            gezahltAn.add(newName);
        }
    }


    @Override
    public String toString(){
        String s="{\n\"description\":\""+beschreibung+"\",\n";
        s+="\"amount\":"+preis+",\n";
        s+="\"infinite\":"+dauerAuftrag+",\n";
        s+="\"loopTime\":\""+loopZeit.name()+"\",\n";
        s+="\"from\":\""+gezahltVon+"\",\n";
        s+="\"to\":\n[\n";
        for(int i=0; i<gezahltAn.size(); i++) {
            if (i != 0) s += ",\n";
            s += "{\"name\":\"" + gezahltAn.get(i) + "\"}";
        }
        s+="],\n";

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String format = formatter.format(datum);
        s+="\"payedOn\":\""+format+"\"\n";
        s+="}";
        return s;
    }

    public String profileOBJString(){
        String s = "{\n";
        s+="\"description\":\""+beschreibung+"\",\n";
        s+="\"amount\":"+preis+",\n";
        s+="\"infinite\":"+dauerAuftrag+",\n";
        s+="\"loopTime\":\""+loopZeit.name()+"\",\n";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String format = formatter.format(datum);
        s+="\"payedOn\":\""+format+"\"\n";
        s+="}";
        return s;
    }
}
