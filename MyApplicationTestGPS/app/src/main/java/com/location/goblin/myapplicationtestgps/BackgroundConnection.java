package com.location.goblin.myapplicationtestgps;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Madan on 02/02/2017.
 */

public class BackgroundConnection extends AsyncTask<String, String, Boolean>{
    URL url;
    HttpURLConnection conn;

    @Override
    protected Boolean doInBackground(String... params) {

                try {
                    url = new URL(GlobalStrings.background);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("id", params[0])
                            .appendQueryParameter("token", params[1])
                            .appendQueryParameter("lat", params[2])
                            .appendQueryParameter("lon", params[3]);
                    String query = builder.build().getEncodedQuery();

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    bw.write(query);
                    bw.flush();
                    bw.close();
                    os.close();
                    conn.connect();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    int response_code = conn.getResponseCode();
                    if (response_code == HttpURLConnection.HTTP_OK) {
                        InputStream is = conn.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));

                        StringBuilder reply = new StringBuilder();
                        String text;

                        while ((text = br.readLine()) != null) {
                            reply.append(text);
                        }
                        System.out.println(reply.toString());
                    } else {
                        System.out.println("Connection no OK !");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    conn.disconnect();
                }


        return null;
    }
}
