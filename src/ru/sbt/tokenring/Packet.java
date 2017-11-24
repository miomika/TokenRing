package ru.sbt.tokenring;
import java.util.Date;
public class Packet {
    private int number = 128;
    private String packet;
    private Date date = new Date();

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

    public Date getDate(){
        return date;
    }
}
