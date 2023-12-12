package practise;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.*;
import java.sql.*;
import java.io.*;

/**
 * @author Notebook
 */
public class MiniServer extends Thread {
    protected static Socket socket;
    //==========================================

    String stroka;
    private WebHandl handler;
    //=======================================================
    public MiniServer(Socket clientSocket, String stroka) throws SQLException, ClassNotFoundException {
        this.socket = clientSocket;
        this.stroka = stroka;
        handler = new WebHandl();
    }

    @Override
    public void run() {
        InputStream inp = null;
        DataOutputStream out2 = null;
        PrintWriter out;
        try {
            inp = socket.getInputStream();

            out2 = new DataOutputStream(socket.getOutputStream());
            out = new PrintWriter(new OutputStreamWriter(out2, "UTF-8"));
        } catch (IOException e) {
            return;
        }
        while (true) {
            try {
                boolean flag = true;
                while (flag == true) {
                    byte[] readmessage = new byte[1000000];
                    inp.read(readmessage);
                    String tempString = new String(readmessage, 0, readmessage.length);
                    if (tempString.trim().compareTo("End") == 0) {
                        System.out.println("Closed");
                        flag = false;
                    } else {
                        stroka = stroka + tempString;
                        String arrStr[] = tempString.split(">>");
                        handler.Receiver(arrStr, out);
                    }
                }
            } catch (IOException e) {
            } catch (SQLException t) {
                t.printStackTrace();
            } finally {
                try {
                    System.out.println("Client logged out");
                    inp.close();
                    out.close();
                    socket.close();
                    return;
                } catch (IOException e) {

                }
            }
        }
    }
}
