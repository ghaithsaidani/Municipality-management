package MODÉLES;
import CONTROLLERS.ConsulterEvenement_controller;
import CONTROLLERS.Consulter_Depense_Controller;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Depense {

    private static AtomicInteger count()
    {
        AtomicInteger i=new AtomicInteger();
        if(Consulter_Depense_Controller.Depense_list().isEmpty())
        {
            i.set(0);
        }
        else
        {
            i.set(Consulter_Depense_Controller.Depense_list().get(Consulter_Depense_Controller.Depense_list().size()-1).getNum());
        }
        return i;
    }

    private int num;
    private float montant ;
    private String motif ;
    private String description ;
    private LocalDate date ;

     public Depense(int num ,float montant, String motif, LocalDate date, String description) {
        this.num=num;
        this.montant = montant;
        this.motif = motif;
        this.description = description;
        this.date = date;

    }

    public Depense(float montant, String motif, LocalDate date, String description) {
        this.num=count().incrementAndGet();
        this.montant = montant;
        this.motif = motif;
        this.description = description;
        this.date = date;
    }

    public Depense(int num,float montant, String motif, LocalDate date) {
        this.num=num;
        this.montant = montant;
        this.motif = motif;
        this.date = date;
    }

    public Depense(float montant, String motif, LocalDate date) {
        this.num=count().incrementAndGet();
        this.montant = montant;
        this.motif = motif;
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public float getMontant() {
        return montant;
    }

    public void setMantant(int mantant) {
        this.montant = montant;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

