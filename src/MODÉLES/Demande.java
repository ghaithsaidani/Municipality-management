package MODÉLES;

import CONTROLLERS.Consulter_Demande_Controller;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Demande {
    private static AtomicInteger count()
    {
        AtomicInteger i=new AtomicInteger();
        if(Consulter_Demande_Controller.Demande_list().isEmpty())
        {
            i.set(0);
        }
        else
        {
            i.set(Consulter_Demande_Controller.Demande_list().get(Consulter_Demande_Controller.Demande_list().size()-1).getNum());
        }
        return i;
    }
    private int num;
    private String nom,prenom;
    private int cin;
    private String adresse;
    private int tel;
    private String type;
    private LocalDate date_debut,date_fin;
    private String description;

    public Demande(int num, String nom, String prenom, int cin, String adresse, int tel, String type, LocalDate date_debut, LocalDate date_fin, String description) {
        this.num = num;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.adresse = adresse;
        this.tel = tel;
        this.type = type;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description = description;
    }

    public Demande(int num, String nom, String prenom, int cin, String adresse, int tel, String type, LocalDate date_debut, LocalDate date_fin) {
        this.num = num;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.adresse = adresse;
        this.tel = tel;
        this.type = type;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Demande(String nom, String prenom, int cin, String adresse, int tel, String type, LocalDate date_debut, LocalDate date_fin, String description) {
        this.num=count().incrementAndGet();
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.adresse = adresse;
        this.tel = tel;
        this.type = type;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description = description;
    }

    public Demande(String nom, String prenom, int cin, String adresse, int tel, String type, LocalDate date_debut, LocalDate date_fin) {
        this.num=count().incrementAndGet();
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.adresse = adresse;
        this.tel = tel;
        this.type = type;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Demande(int num) {
        this.num = num;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
