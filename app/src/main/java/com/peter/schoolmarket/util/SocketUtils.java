package com.peter.schoolmarket.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by PetterChen on 2017/5/6.
 */

public class SocketUtils {

    public static Socket initSocket(String site, int port){
        try {
            return new Socket(site,port);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void sendMsg(Socket client, String msg){
        try{
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),"utf-8"));
            writer.write(msg);
            writer.flush();
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static String receiverMsg(Socket client){
        String msg = null;
        try{
            BufferedReader reader=new BufferedReader(new InputStreamReader(client.getInputStream(),"utf-8"));
            if(reader.ready()) {
                msg = reader.readLine();
            }
            reader.close();
            return msg;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void closeSocket(Socket client){
        try{
            client.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
