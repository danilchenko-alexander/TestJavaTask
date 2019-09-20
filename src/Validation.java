import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alexander on 19.09.2019.
 */
public class Validation {


    public static boolean validatePhoneNum(String phoneNumber){
        Pattern pattern = Pattern.compile("^((\\+?375)([0-9]{9}))$");
        Matcher matcher = pattern.matcher(phoneNumber);

        if(matcher.matches())
            return true;
        return false;
    }

    public static boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("^([A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]{2,4}\\b)$");
        Matcher matcher = pattern.matcher(email);

        if(matcher.matches())
            return true;
        return false;
    }
}
