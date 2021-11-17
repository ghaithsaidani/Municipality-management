package MODÉLES;


public class Compte {
    private Employé emp;
    private String e_mail,poste,password;



    public Compte(Employé emp, String e_mail, String poste, String password) {
        this.emp= emp;
        this.e_mail = e_mail;
        this.poste = poste;
        this.password = password;
    }


    public Compte(Employé emp) {
        this.emp= emp;
    }

    public Employé getEmp() {
        return this.emp;
    }



    public void setEmp(Employé emp) {
        this.emp = emp;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getX() {
        return emp.getId();
    }


}
