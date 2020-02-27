package test.powerlog.mobile.springboot.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class SendMsgService_New {
    public static String NewSend(String sendUrl, String jsonValue) throws IllegalStateException {

        String inputLine = null;
        StringBuffer outResult = new StringBuffer();

        try{
            URL url = new URL(sendUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("x-ncp-auth-key", "SmxiQBvfpnf1eaPiJByC");
            conn.setRequestProperty("x-ncp-service-secret", "7af448a8032745a68fe8dd7bc5ea000b");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            OutputStream os = conn.getOutputStream();
            os.write(jsonValue.getBytes("UTF-8"));
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((inputLine = in.readLine()) != null) {
                outResult.append(inputLine);
            }

            conn.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }

        return outResult.toString();
    }
}
