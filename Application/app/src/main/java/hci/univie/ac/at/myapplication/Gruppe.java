package hci.univie.ac.at.myapplication;

import java.util.ArrayList;

/**
 * Created by Benne on 04.05.2018.
 */

public class Gruppe {
    private String name;
    private ArrayList<String> teilnehmerNamen;
    private ArrayList<Zahlung> teilnehmerZahlungen;

    public Gruppe(){
        teilnehmerNamen = new ArrayList<String>();
        teilnehmerZahlungen = new ArrayList<Zahlung>();
    }

    public Gruppe(String n, ArrayList<String> arr){
        teilnehmerZahlungen = new ArrayList<Zahlung>();
        name = n;
        teilnehmerNamen = arr;
    }
    public void setName(String newName){
        name = newName;
    }
    public void addMember(String newMemberName){
        teilnehmerNamen.add(newMemberName);
    }
    public void addBill(Zahlung newBill){
        teilnehmerZahlungen.add(newBill);
    }

    public boolean memberInGroup(String memberName){
        for(String name : teilnehmerNamen){
            if(name.equals(memberName))return true;
        }
        return false;
    }

    public ArrayList<String> getMembers(){
        return teilnehmerNamen;
    }
    public ArrayList<Zahlung> getBills(){
        return teilnehmerZahlungen;
    }
    public String getName(){
        return name;
    }


    @Override
    public String toString(){
        String s="{\"name\":\""+name+"\",";


        s+="\n\"members\":\n[\n";
        for(int i =0; i<teilnehmerNamen.size(); i++){
            if(i!=0)s+=",\n";
            s+="{\"name\":\""+teilnehmerNamen.get(i)+"\"}";
        }
        s+="\n],";

        s+="\"bills\":\n[\n";
        for (int i =0; i<teilnehmerZahlungen.size(); i++){
            if(i!=0)s+=",\n";
            s+=teilnehmerZahlungen.get(i).toString();
        }
        s+="\n]\n}";
        return s;
    }


}
