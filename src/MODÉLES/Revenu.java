package MODÉLES;

import CONTROLLERS.Consulter_Revenu_Controller;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Revenu {


        private static AtomicInteger count()
        {
            AtomicInteger i=new AtomicInteger();
            if(Consulter_Revenu_Controller.Revenu_list().isEmpty())
            {
                i.set(0);
            }
            else
            {
                i.set(Consulter_Revenu_Controller.Revenu_list().get(Consulter_Revenu_Controller.Revenu_list().size()-1).getNum());
            }
            return i;
        }

        private int num;
        private float montant,rev ;
        private String description ;
        private LocalDate date ;

        public Revenu(int num ,float montant, float rev, LocalDate date, String description) {
            this.num=num;
            this.montant = montant;
            this.rev = rev;
            this.description = description;
            this.date = date;

        }

        public Revenu(float montant, float rev, LocalDate date, String description) {
            this.num=count().incrementAndGet();
            this.montant = montant;
            this.rev = rev;
            this.description = description;
            this.date = date;
        }

    public Revenu(int num, float montant, float rev, LocalDate date) {
        this.num = num;
        this.montant = montant;
        this.rev = rev;
        this.date = date;
    }

    public Revenu(float montant, float rev, LocalDate date) {
        this.num=count().incrementAndGet();
        this.montant = montant;
        this.rev = rev;
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }



    public void setMontant(float montant) {
        this.montant = montant;
    }



    public void setRev(float rev) {
        this.rev = rev;
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

    public float getMontant() {
        return montant;
    }

    public float getRev() {
        return rev;
    }
}
