package MODÉLES;

import CONTROLLERS.ConsulterEvenement_controller;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Evenement {

    private static AtomicInteger count()
    {
        AtomicInteger i=new AtomicInteger();
        if(ConsulterEvenement_controller.Evenement_list().isEmpty())
        {
            i.set(0);
        }
        else
        {
            i.set(ConsulterEvenement_controller.Evenement_list().get(ConsulterEvenement_controller.Evenement_list().size()-1).getNum());
        }
        return i;
    }

    private String Adresse, type, etat, nom,description;
    private int num;
    private LocalDate date_debut, date_fin;
    private float budget;



    public Evenement(int num, String nom,  String etat,String type, LocalDate date_debut, LocalDate date_fin, String Adresse,float budget,String description) {
        this.num = num;
        this.nom = nom;
        this.type = type;
        this.etat = etat;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.Adresse = Adresse;
        this.description=description;
        this.budget=budget;
    }

    public Evenement(String nom, String etat,String type,   LocalDate date_debut, LocalDate date_fin,String adresse,float budget,String description) {
        this.num=count().incrementAndGet();
        this.type = type;
        this.etat = etat;
        this.nom = nom;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.Adresse = adresse;
        this.description=description;
        this.budget=budget;
    }

    public Evenement(int num, String nom,  String etat,String type, LocalDate date_debut, LocalDate date_fin, String Adresse,float budget) {
        this.num = num;
        this.nom = nom;
        this.type = type;
        this.etat = etat;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.Adresse = Adresse;
        this.budget=budget;
    }

    public Evenement(int num) {
        this.num=num;
    }


    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }
}

