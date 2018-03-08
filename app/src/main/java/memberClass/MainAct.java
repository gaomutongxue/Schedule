package memberClass;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;

import memberClass.User;
import memberClass.Affair;

public class MainAct {
	static final String Address = "http://120.79.223.146:8080/schedule/main";
	static final int Success = 0;
	static final int Failed = 1;
	
    private static String readResult(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        return sb.toString();
    }

    
	public static ArrayList<Affair> affairQuery(String username) throws IOException, Exception {
		String path = Address + "?username=" + username;
		String ans = new String();
		
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        conn.setReadTimeout(3000);
        conn.setConnectTimeout(3000);
        
        int code = conn.getResponseCode();
        
        if (code == 200) {
        	InputStream is = conn.getInputStream();
        	ans = readResult(is);
        }
//        System.out.println(ans);
        
        ArrayList<Affair> result = new ArrayList<Affair>();
        String[] affairs = ans.split("\n");
        for (int i = 0; i < affairs.length; i++) {
        	String[] col = affairs[i].split(",");
        	Affair temp = new Affair();
        	
        	String affairName = col[0];
        	int type = Integer.parseInt(col[1]);
        	int level = Integer.parseInt(col[2]);
        	String note = col[3];
        	String startTime = col[4];
        	String endTime = col[5];
        	
        	temp.set_affair(affairName, username, type, level, note, startTime, endTime);
        	result.add(temp);
        }
        
   /*     for (int i = 0; i < result.size(); i++) {
        	PrintWriter out = new PrintWriter(System.out, true);
        	result.get(i).output(out);
        }*/
        return result;
	}
	
	
	public static void main (String[] args) {
		try {
			ArrayList<Affair> test = affairQuery("root");
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
}
