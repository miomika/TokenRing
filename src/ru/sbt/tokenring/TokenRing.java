package ru.sbt.tokenring;


import java.util.concurrent.ConcurrentLinkedQueue;

public class TokenRing {
    private ConcurrentLinkedQueue<Packet>[] packets;
    private Thread[] nodes;
    private int cnt; //finished packets

    public TokenRing(int n) {
        packets = new ConcurrentLinkedQueue[n];
        nodes = new Thread[n];
        for (int i = 0; i < n; i++) {
            packets[i] = new ConcurrentLinkedQueue<Packet>();
        }
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(packets[i], packets[(i + 1) % n], this);
        }
        cnt = 0;
    }


    public void start(){
        for(int i=0; i< nodes.length; i++){
            nodes[i].start();
        }
    }

    public void stop(){
        Node.flag = false;
    }

    public void sendPacket(Packet packet){
        packets[0].add(packet);
    }

    public void finishedPacket(Packet packet){
        cnt++;
        //System.out.printf("Count of dead packets: " + cnt + " Packet: " + packet.toString()+"\n");
    }

}
