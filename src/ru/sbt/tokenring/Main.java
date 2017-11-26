package ru.sbt.tokenring;

import java.io.*;


public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        TokenRing token = new TokenRing(10);
        token.start();
        for(int i=0; i<2000000; i++){
            Packet packet = new Packet(128,i+" packet");
            token.sendPacket(packet);
        }
        Thread.sleep(100000);
        token.stop();
        token.statistics();
        token.getWrite();

    }

}
