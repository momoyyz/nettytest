package com.netty.demo2;

import java.io.IOException;
import java.util.Scanner;

public class ServerStart {
static  Server server;
    public static void main(String[] args) {
         server = new Server(8080);
        Scanner input = new Scanner(System.in);
        String infoString = "";
        while (true) {
            infoString = input.nextLine();
            RequestInfo req = new RequestInfo();
            req.setType((byte) 1);
            req.setInfo(infoString);
            server.sendMessage(req);
        }
    }

//    public static void star() throws IOException {
//        Server server = new Server(8080);
//        Scanner input = new Scanner(System.in);
//        String infoString = "";
//        while (true) {
//            infoString = input.nextLine();
//            server.sendMessage(req);
//        }
//    }

}


