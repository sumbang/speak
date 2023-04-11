package tv.wouri.speak.config;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Random;

public class Setting {

    public static int PAGE_SIZE = 100;

    public static String appName = "Ghomala";

    public static final String SECRET = "SygemAppsForEmployeConge2022ApplicationWebAndMobile";
    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 30; // 30 days
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final int TOKEN_ACTIVATION_TIME = 3;
    public static final String HEADER_STRING = "Authorization";

    public static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static SecureRandom rnd = new SecureRandom();

    public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

    //public static final String APPS_URL = "http://185.163.126.213:8080/sygem";
    public static final String APPS_URL = "http://127.0.0.1:8080/speak";

    public static String randomToken() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 20;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return  generatedString+="-"+timestamp.getTime();

    }

    public static String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static boolean validatePassword(String pass) {
        if(pass.length() < 8) return false;
        else return true;
    }

    public static String getStatut(String val) {
        if(val.equals("SAI")) return "INITIALISER";
        else if(val.equals("VAL")) return "VALIDER";
        else if(val.equals("PAI")) return "PAYER";
        else return "ANNULER";
    }
}
