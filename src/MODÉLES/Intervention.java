package MODÉLES;

import CONTROLLERS.ConsulterIntervention_Controller;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Intervention {
    private static AtomicInteger count()
    {
        AtomicInteger i=new AtomicInteger();
        if(ConsulterIntervention_Controller.Rec_list().isEmpty())
        {
            i.set(0);
        }
        else
        {
            i.set(ConsulterIntervention_Controller.Rec_list().get(ConsulterIntervention_Controller.Rec_list().size()-1).getNum());
        }
        return i;
    }
    private int num,tel,cin;
    private String nom,prenom,categorie,sujet,description;
    private LocalDate date_reclamation;

    public Intervention(int num, String nom, String prenom, int cin, int tel, String categorie, String sujet, LocalDate date_reclamation,String description) {
        this.num = num;
        this.tel = tel;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.categorie = categorie;
        this.sujet = sujet;
        this.date_reclamation = date_reclamation;
        this.description = description;
    }

    public Intervention(int num, String nom, String prenom, int cin, int tel, String categorie, String sujet, LocalDate date_reclamation) {
        this.num = num;
        this.tel = tel;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.categorie = categorie;
        this.sujet = sujet;
        this.date_reclamation = date_reclamation;
    }

    public Intervention(String nom, String prenom, int cin, int tel, String categorie, String sujet, LocalDate date_reclamation,String description) {
        this.num=count().incrementAndGet();
        this.tel = tel;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.categorie = categorie;
        this.sujet = sujet;
        this.date_reclamation = date_reclamation;
        this.description = description;
    }

    public Intervention(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public LocalDate getDate_reclamation() {
        return date_reclamation;
    }

    public void setDate_reclamation(LocalDate date_reclamation) {
        this.date_reclamation = date_reclamation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
