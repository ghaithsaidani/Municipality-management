package MODÉLES;

import java.time.LocalDate;
import java.util.Date;

public class Employé {
    private String nom, prename,adr,adr_mail,diplome,poste,etat;
    private int id, tel, anne;
    private LocalDate date;


    public Employé(String nom, String prename, int id, LocalDate date, String adr, String adr_mail, int tel, String diplome,  String poste ,int anne,String etat) {
        this.nom = nom;
        this.prename = prename;
        this.adr = adr;
        this.adr_mail = adr_mail;
        this.diplome = diplome;
        this.poste = poste;
        this.id = id;
        this.tel = tel;
        this.anne = anne;
        this.date=date;
        this.etat=etat;
    }


    public Employé(int id) {
        this.id = id;
    }

    public Employé(int id,String nom, String prename, int tel, String poste,String etat) {
        this.nom = nom;
        this.prename = prename;
        this.poste = poste;
        this.id = id;
        this.tel = tel;
        this.etat=etat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getAdr_mail() {
        return adr_mail;
    }

    public void setAdr_mail(String adr_mail) {
        this.adr_mail = adr_mail;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public int getAnne() {
        return anne;
    }

    public void setAnne(int anne) {
        this.anne = anne;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
