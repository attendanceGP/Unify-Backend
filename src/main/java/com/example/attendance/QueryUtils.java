package com.example.attendance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class QueryUtils {
    public static String makeHttpRequest(String stringUrl) throws Exception{
        URL url = new URL(stringUrl);

        String jsonResponse = "";

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responeCode = conn.getResponseCode();

        if(responeCode != 200){
            throw new RuntimeException("HttpResponseCode: " + responeCode);
        }else{
            jsonResponse = readFromStream(conn.getInputStream());
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
