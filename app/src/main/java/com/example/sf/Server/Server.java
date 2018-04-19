package com.example.sf.Server;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by sf on 18-3-13.
 */

public class Server {
    private Handler handler;
    URL url=null;
    /**
     * @param handler 用于捕获从子线程发出消息的Hander
     * */
    public Server(Handler handler,URL url){
        this.url=url;
        this.handler=handler;
    }
    public Server(Handler handler,String url){
        try {
            this.url=new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.handler=handler;
    }
    /**
     * @param s  需要向服务器发送的字符串*/
    public void post(String s){
        SerConnect serConnect=new SerConnect(url,s,handler);
        Thread thread=new Thread(serConnect);
        thread.start();

    }
}
/**
 * 向服务器发送消息并获取来自服务器的相应的字符串*/
class SerConnect implements Runnable{

    private URL urlObj=null;
    private String json=null;
    private Handler handler=null;
    /**
     * @param url 请求的网址
     * @param json 要向网站提交的数据*/
    public SerConnect(String url,String json,Handler handler) {
        try {
            this.urlObj=new URL(url);
            this.json=json;
            this.handler=handler;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public SerConnect(URL url,String json,Handler handler) {
            this.urlObj=url;
            this.json=json;
            this.handler=handler;
    }
    @Override
    public void run() {
        HttpURLConnection connection=null;
        try {
            connection=(HttpURLConnection)urlObj.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setReadTimeout(120000);
            connection.setConnectTimeout(120000);
            
            //添加 请求内容
            try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8")) {
                out.write(json);
                out.flush();
            }
            InputStream inputStream=connection.getInputStream();
            System.out.println(connection.getResponseMessage());
            connection.getResponseMessage();
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
//            
            String response="";
            String s;
            while((s=reader.readLine())!=null) {
                response += s;
            }
            Message message=new Message();
            message.obj=response;
            handler.sendMessage(message);
            
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
       
    }
        
        
        
    }
