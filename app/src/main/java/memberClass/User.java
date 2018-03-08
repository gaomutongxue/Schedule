package memberClass;

/**
 * Created by hasee on 2018/2/8.
 */
public class User {
    String username;
    String password;
    String phoneNumber;

    public int sex;
    public String name;
    public int age;


    private static boolean username_rule(char ch)
    {
        if (ch > 'Z' && ch < 'a')
            return false;
        if (ch > '9' && ch < 'A')
            return false;
        if (ch > 'z' || ch < '0')
            return false;
        return true;
    }


    public static boolean checkup_username(String ck_username)
    {
        for (int i = 0; i < ck_username.length(); i++) {
            if ( !username_rule((ck_username.charAt(i))) )
                return false;
        }

        return true;
    }


    private static boolean password_rule(char ch)
    {
        if (ch < '0' || ch > 'z')
            return false;
        if (ch > '9' && ch < 'A')
            return false;

        return true;
    }


    public static boolean checkup_password(String ck_password)
    {
        for (int i = 0; i < ck_password.length(); i++) {
            if ( !password_rule((ck_password.charAt(i))) )
                return false;
        }

        return true;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() { return password; }


    public String getPhoneNumber() { return phoneNumber; }


    public boolean checkRules(String input_password)
    {
        if (password == input_password)
            return true;
        return false;
    }


    public int changePassword(String old_password, String new_password)
    {
        if (password != old_password)
            return 1;
        password = new_password;
        return 0;
    }


    public void registerUser(String new_username, String new_password, int new_sex, String new_name,
                        int new_age, String new_phoneNumber)
    {
        username = new_username;
        password = new_password;
        sex = new_sex;
        name = new_name;
        age = new_age;
        phoneNumber = new_phoneNumber;
    }
}
