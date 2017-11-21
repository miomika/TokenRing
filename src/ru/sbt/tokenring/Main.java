package ru.sbt.tokenring;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TokenRing token = new TokenRing(10);
        token.start();
        for(int i=0; i<20000; i++){
            token.sendPacket(new Packet(128,i+" packet"));
        }
        Thread.sleep(100000);
        token.stop();
    }

}
