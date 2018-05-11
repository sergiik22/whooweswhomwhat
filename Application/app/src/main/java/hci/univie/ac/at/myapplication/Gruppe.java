package hci.univie.ac.at.myapplication;

import android.util.Log;

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

    public void changeName(String oldName, String newName){
        for (Zahlung z:teilnehmerZahlungen){
            if(z.hasMember(oldName))z.changeName(oldName,newName);
        }
        teilnehmerNamen.remove(oldName);
        teilnehmerNamen.add(newName);
    }

    public boolean hasMember(String memberName){
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

    public double calculateForMember(int memberIndex){
        String memberName = teilnehmerNamen.get(memberIndex);
        double val=0;
        //Log.i("CALCSTART",memberName);
        for(Zahlung z:MainActivity.mainGruppe.getBills()){
            if(z.getPayer().equals(memberName) || z.getPayed().contains(memberName)){
                int totalMembers = z.getPayed().size();
                if(z.getPayer().equals(memberName)){
                    val += z.getPrice();
                }
                val -= z.getPrice()/(double) totalMembers;
               // Log.i("CALCSTAT",val+"");
            }
        }
       // Log.i("CALCEND",memberName);
        return val;
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
