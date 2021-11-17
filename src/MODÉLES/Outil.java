package MODÉLES;

import CONTROLLERS.ConsulterOutil_Controller_Admin;

import java.util.concurrent.atomic.AtomicInteger;


public class Outil {
    private static  AtomicInteger count()
    {
        AtomicInteger i=new AtomicInteger();
        if(ConsulterOutil_Controller_Admin.outlist().isEmpty())
        {
            i.set(0);
        }
        else
        {
            i.set(ConsulterOutil_Controller_Admin.outlist().get(ConsulterOutil_Controller_Admin.outlist().size()-1).getNum());
        }
        return i;
    }

    private int num;
    private String type;
    private String etat;
    private int quantité;



    public Outil(int num) {
        this.num = num;
    }

    public Outil(int num, String type, String etat, int quantité) {
        this.num =num;
        this.type = type;
        this.etat = etat;
        this.quantité = quantité;
    }

    public Outil(String type, String etat, int quantité) {
        this.num=count().incrementAndGet();
        this.type = type;
        this.etat = etat;
        this.quantité = quantité;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getQuantité() {
        return quantité;
    }

    public void setQuantité(int quantité) {
        this.quantité = quantité;
    }

}
