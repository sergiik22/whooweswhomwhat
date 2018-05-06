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
        String s="\n{\n"+name;
        s+="\nMembers:\n[\n";
        for(String name:teilnehmerNamen){
            s+="\t"+name+";\n";
        }
        s+="]\nBills:\n[";
        for (Zahlung z : teilnehmerZahlungen){
            s+="\t"+z.toString()+";\n";
        }
        s+="]\n}";
        return s;
    }


}
