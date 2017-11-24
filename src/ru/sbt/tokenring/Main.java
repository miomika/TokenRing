package ru.sbt.tokenring;

import java.io.*;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        TokenRing token = new TokenRing(10);
        token.start();
        for(int i=0; i<2000000; i++){
            token.sendPacket(new Packet(128,i+" packet"));
        }
        Thread.sleep(100000);
        token.stop();
        token.printStatistics();
        token.getWrite();


    }

}
