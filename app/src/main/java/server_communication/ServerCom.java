package server_communication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


 //////Created by kyle on 2016/03/27.//////

public class ServerCom {


    public static final String PREFS_NAME = "privateData";

    public static String talkToServer(String url, String query) {
        List<Future<String>> list = new ArrayList<Future<String>>();

        int NTHREDS = 1;

        ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);

        Log.i("servercom", url + query);

        CallableObject callable = new CallableObject(url, query);

        Future<String> submit = executor.submit(callable);
        list.add(submit);

        executor.shutdown();

        // Wait until all threads are finish
        while (!executor.isTerminated()) {
        }

        for (Future<String> future : list) {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    public void SaveToDevice(String key, String data, Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, data);

        editor.apply();

    }
    public String ReadFromDevice(String key, Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);

        return settings.getString(key, "");

    }

}
