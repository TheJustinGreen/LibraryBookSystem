package server_communication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;


 /////// Created by kyle on 2016/03/22.///////

public class CallableObject implements Callable<String> {
    public final String url;
    public final String Query;


    public String getJson(String url, String params) {

        InputStream is = null;
        String response = "";

        try {
            URL _url = new URL(url);
            HttpURLConnection urlConn = (HttpURLConnection) _url.openConnection();

            urlConn.setRequestMethod("POST");

            urlConn.setDoOutput(true);

//            Log.i("Connect", urlConn.toString());

            urlConn.connect();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream()));

            writer.write(params);
            writer.flush();
            writer.close();


            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = urlConn.getInputStream();// is is inputstream
                Log.i("Conn", is + "");

            } else {
                is = urlConn.getErrorStream();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = null;
            if (is != null) {
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            }
            StringBuilder sb = new StringBuilder();
            String line;
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }
            if (is != null) {
                is.close();
            }
            response = sb.toString();
            Log.e("JSON", response);
        }
        catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        Log.e("responding", response);

        return response;
    }

    @Override
    public String call() throws Exception {
        return getJson(url, Query);
    }

    public CallableObject(String url, String query) {
        this.url = url;
        this.Query = query;
    }

}