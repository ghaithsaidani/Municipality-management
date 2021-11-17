package VALIDATION;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

    public static boolean verifieremail(String s)
    {
        String emailregex="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailpattern=Pattern.compile(emailregex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailpattern.matcher(s);
        return matcher.find();
    }

    public static boolean verifierchaine(String str) {
        return str.matches("[A-Za-z]+");
    }

    public static boolean verifierdatenaissance(LocalDate date) {
        Period p=Period.between(date, LocalDate.now());
        return p.getYears()>18;
    }

    public static boolean verifierint(String str) {
        return str.matches("[0-9]+");
    }

    public static boolean verifierpassword(String str) {
        return str.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    }

    public static boolean verifiermatricule(String str) {
        return str.matches("([0-9]{2,3}[A-Za-z]{3,}[0-9]{2,3})");
    }

    public static boolean verifierId(String str) {
            return str.matches("\\d{8}");
        }

    public static boolean verifierTel(String str) {
        return str.matches("\\d{8}");
    }

}
