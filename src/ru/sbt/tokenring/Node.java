package ru.sbt.tokenring;



import java.util.concurrent.ConcurrentLinkedQueue;

public class Node extends Thread {
    private ConcurrentLinkedQueue<Packet> inSocket;
    private ConcurrentLinkedQueue<Packet> outSocket;
    private TokenRing token;
    public static volatile boolean flag = true;



    public Node(ConcurrentLinkedQueue<Packet> inSocket, ConcurrentLinkedQueue<Packet> outSocket, TokenRing token) {
        this.inSocket = inSocket;
        this.outSocket = outSocket;
        this.token = token;
    }

    @Override
    public void run() {
        while (Node.flag){
            if(!inSocket.isEmpty()){
                Packet packet = inSocket.poll();
                packet.decrease();
                if (packet.isEmpty()){
                    token.finishedPacket(packet);
                } else {
                    outSocket.add(packet);
                }
            }
        }
    }
}
