package MODÉLES;

import CONTROLLERS.Consulter_Doleance_Controller;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Doleance {

        private static AtomicInteger count()
        {
            AtomicInteger i=new AtomicInteger();
            if(Consulter_Doleance_Controller.Dol_list().isEmpty())
            {
                i.set(0);
            }
            else
            {
                i.set(Consulter_Doleance_Controller.Dol_list().get(Consulter_Doleance_Controller.Dol_list().size()-1).getNum());
            }
            return i;
        }

        private int num,tel;
        private String nom,prenom,type,description,adresse,titre;
        private LocalDate date_reclamation;

        public Doleance(int num,String titre ,String type, LocalDate date_reclamation, String nom, String prenom,  String adresse, int tel,  String description) {
            this.num = num;
            this.tel = tel;
            this.nom = nom;
            this.titre=titre;
            this.adresse=adresse;
            this.prenom = prenom;
            this.type = type;
            this.description = description;
            this.date_reclamation = date_reclamation;
        }

    public Doleance(int num,String titre ,String type, LocalDate date_reclamation, String nom, String prenom,  String adresse, int tel) {
        this.num = num;
        this.tel = tel;
        this.nom = nom;
        this.titre=titre;
        this.adresse=adresse;
        this.prenom = prenom;
        this.type = type;
        this.date_reclamation = date_reclamation;
    }

    public Doleance(String titre ,String type, LocalDate date_reclamation, String nom, String prenom,  String adresse, int tel,  String description) {
        this.num =count().incrementAndGet();
        this.tel = tel;
        this.nom = nom;
        this.titre=titre;
        this.adresse=adresse;
        this.prenom = prenom;
        this.type = type;
        this.description = description;
        this.date_reclamation = date_reclamation;
    }

    public Doleance(String titre ,String type, LocalDate date_reclamation, String nom, String prenom,  String adresse, int tel) {
        this.num =count().incrementAndGet();
        this.tel = tel;
        this.nom = nom;
        this.titre=titre;
        this.adresse=adresse;
        this.prenom = prenom;
        this.type = type;
        this.date_reclamation = date_reclamation;
    }

    public Doleance(int num) {
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAdresse() {
            return adresse;
        }

        public void setAdresse(String adresse) {
            this.adresse = adresse;
        }

        public String getTitre() {
            return titre;
        }

        public void setTitre(String titre) {
            this.titre = titre;
        }

        public LocalDate getDate_reclamation() {
            return date_reclamation;
        }

        public void setDate_reclamation(LocalDate date_reclamation) {
            this.date_reclamation = date_reclamation;
        }
    }

