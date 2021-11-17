package MODÉLES;

import CONTROLLERS.ConsulterVehicule_Controller_Admin;

import java.util.concurrent.atomic.AtomicInteger;

public class Vehicule {
    private static AtomicInteger count()
    {
        AtomicInteger i=new AtomicInteger();
        if(ConsulterVehicule_Controller_Admin.vehlist().isEmpty())
        {
            i.set(0);
        }
        else
        {
            i.set(ConsulterVehicule_Controller_Admin.vehlist().get(ConsulterVehicule_Controller_Admin.vehlist().size()-1).getNum());
        }
        return i;
    }

    private int num;
    private String matricule;
    private String type;
    private String etat;
    private int annee;



    public Vehicule(int num) {
        this.num = num;
    }

    public Vehicule(int num,String matricule, String type, String etat, int annee) {
        this.num =num;
        this.matricule=matricule;
        this.type = type;
        this.etat = etat;
        this.annee = annee;
    }

    public Vehicule(String matricule,String type, String etat, int annee) {
        this.num=count().incrementAndGet();
        this.matricule=matricule;
        this.type = type;
        this.etat = etat;
        this.annee = annee;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getMatricule() { return matricule; }

    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) { this.etat = etat; }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

}
