package Brukere;

public class Validering {

    public static boolean Regex(String regex, String... strings) {
        for (String s : strings) {
            if (!s.matches(regex)) {
                return false;
            }
        }
        return true;
    }

    public static boolean navn(String navn) {
        String regex = ""; //regexen er forskjellige for navn, email, tlf og eventuelle andre
        boolean sjekk = !navn.isEmpty() && Regex(navn, regex);
        return sjekk;
    }

    public static boolean passord(String passord) { //kanskje dropp validering for passord
        String regex = "";
        boolean sjekk = !passord.isEmpty() && Regex(passord, regex);
        return sjekk;
    }

    public static boolean Email(String email) {
        String regex = "";
        boolean sjekk = !email.isEmpty() && Regex(email, regex);
        return sjekk;
    }

    public static boolean tlf(String tlf) {
        String regex = "";
        boolean sjekk = !tlf.isEmpty() && Regex(tlf, regex);
        return sjekk;
    }

}
