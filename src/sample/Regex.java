package sample;

public class Regex {

    public static boolean validering(String regex, String... strings) {
        for (String str : strings) {

            if (!str.matches(regex) || str.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean navn(String navn) {
        boolean validering = Regex.validering("[A-ZÆØÅ][a-zæøå]+(\\s[A-ZÆØÅ][a-zæøå]*)", navn);
        if (validering) {
            return true;
        }
        return false;
    }

    public static boolean epost(String mail) {
        boolean validering = mail != null && mail.indexOf("@") != -1 && Regex.validering("(?:[A-ZÆØÅa-zæøå0" +
                "-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x" +
                "1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[" +
                "a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9" +
                "]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-" +
                "\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", mail);
        if (validering) {
            return true;
        }
        return false;
    }

    public static boolean tlf(String tlf) {
        boolean validering = tlf != null && !(tlf.isEmpty()) && Regex.validering("[+()\\s\\-0-9][\\s+()\\-0-9][\\s+()\\-0-9]{8,15}+", tlf);
        if (!validering) {
            validering = tlf != null && !(tlf.isEmpty()) && Regex.validering("[+()\\s\\-0-9]{8,15}+", tlf);
        }
        return validering;
    }
}
