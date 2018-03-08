package sqlClient;

/**
 * Created by hasee on 2018/2/14.
 */

import memberClass.User;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginAndRegister {
    private static String Address = "http://120.79.223.146:8080/schedule/login";
    static final int Success = 0;
    static final int Failed = 1;


    private static String readResult(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }


    public static int loginQuery(String username, String password) throws Exception {
        String path = Address + "?username=" + username + "&password=" + password;
        int ans = Failed;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setReadTimeout(3000);
            conn.setConnectTimeout(3000);

            int code = conn.getResponseCode();

            if (code == 200) {
                InputStream is = conn.getInputStream();
                ans = Integer.parseInt(readResult(is));
                is.close();
            }
            else
                return Failed;

        } catch (IOException e) {
            e.printStackTrace();
            return Failed;
        }

        return ans;
    }


    public static int registerQuery(User newUser) throws Exception, IOException {
        String username = newUser.getUsername();
        String password = newUser.getPassword();
        String phoneNumber = newUser.getPhoneNumber();
        String sex = Integer.toString(newUser.sex);
        String age = Integer.toString(newUser.age);
        String name = newUser.name;


        int ans = Failed;
        URL url = new URL(Address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setReadTimeout(3000);
        conn.setConnectTimeout(3000);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setDoInput(true);

        String data = "username=" + URLEncoder.encode(username, "UTF-8") +
                "&password=" + URLEncoder.encode(password, "UTF-8") +
                "&sex=" + URLEncoder.encode(sex, "UTF-8") +
                "&age=" + URLEncoder.encode(age, "UTF-8") +
                "&name=" + URLEncoder.encode(name, "UTF-8") +
                "&phoneNumber=" + URLEncoder.encode(phoneNumber, "UTF-8");

        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();
        os.close();
        conn.connect();
        if (conn.getResponseCode() == 200) {
            InputStream is = conn.getInputStream();
            ans = Integer.parseInt(readResult(is));
            is.close();
        }
        else
            return Failed;

        return ans;
    }
}