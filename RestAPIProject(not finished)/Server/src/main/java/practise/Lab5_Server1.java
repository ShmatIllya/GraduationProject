package practise;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.net.*;
import java.sql.SQLException;

/**
 * @author Notebook
 */

public class Lab5_Server1 {

    ServerSocket sock;
    String stroka;


    public static void main(String args[]) throws SQLException, ClassNotFoundException {
        MiniServer listener = new MiniServer();
        listener.start();
    }

}

