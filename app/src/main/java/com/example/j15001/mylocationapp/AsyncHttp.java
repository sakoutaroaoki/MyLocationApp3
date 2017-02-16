package com.example.j15001.mylocationapp;

import android.os.AsyncTask;
import android.util.Log;

import  java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class AsyncHttp extends AsyncTask<String, Integer, Boolean> {
    HttpURLConnection urlConnection = null;
    Boolean flg = false;

    double[] name=new double[3];
    double[] value=new double[3];

    public AsyncHttp(double name, double value){
        this.name[0] = name;
        this.value[0] = value;
    }

    //非同期処理ここから
    @Override
    protected  Boolean doInBackground(String... contents){
        String urlinput = "http://192.168.42.200/GPS/post.php";//ローカルIPアドレスなので変更が必要
        try{
            URL url = new URL(urlinput);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            //POST用パラメータ
            String postDataSample = "lat=" + name[0] + "&lng=" + value[0];
            //String postDataSample = "lat=" + 123 + "&lng=" + 123;
            //POSTパラメータ設定
            OutputStream out = urlConnection.getOutputStream();
            out.write(postDataSample.getBytes());
            out.flush();
            out.close();
            Log.d("test", postDataSample);
            //レスポンスを受け取る
            urlConnection.getInputStream();

            flg = true;
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return flg;
    }
}