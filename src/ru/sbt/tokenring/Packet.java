package ru.sbt.tokenring;

public class Packet {
    private int number = 128;
    private String packet;

    public Packet(int number, String packet) {
        this.number = number;
        this.packet = packet;
    }

    public synchronized void decrease(){
        this.number = this.number-1;
    }


    public boolean isEmpty(){
        if (this.number<=0){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return  packet;
    }
}
