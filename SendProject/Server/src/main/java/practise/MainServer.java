package practise;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.net.*;

/**
 * @author Notebook
 */

public class MainServer {

    ServerSocket sock;
    String stroka;


    public MainServer() {

        try {
           // Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Server started");
            sock = new ServerSocket(1488);
            //=========================================================
            //=========================================================
            while (true) {
                Socket client = sock.accept();
                MiniServer mini = new MiniServer(client, stroka);
                mini.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        MainServer classObj = new MainServer();
    }

}

