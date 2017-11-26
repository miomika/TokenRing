package ru.sbt.tokenring;


import java.io.*;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TokenRing {
    private ConcurrentLinkedQueue<Packet>[] packets;
    private Thread[] nodes;
    private int cnt;
    private ConcurrentLinkedQueue<Long> statisticsAverage = new ConcurrentLinkedQueue<Long>();
    private Map<Long, Integer> throughputMap = new TreeMap<Long, Integer>();

    public TokenRing(int n) {
        packets = new ConcurrentLinkedQueue[n];
        nodes = new Thread[n];
        for (int i = 0; i < n; i++) {
            packets[i] = new ConcurrentLinkedQueue<Packet>();
        }
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(packets[i], packets[(i + 1) % n], this);
        }
        cnt = n;
    }


    public void start() {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].start();
        }
    }

    public void stop() {
        Node.flag = false;
    }

    public void sendPacket(Packet packet) {
        packets[0].add(packet);
    }

    public void finishedPacket(Packet packet) {
        long diffSeconds = ((new Date()).getTime() - packet.getDate().getTime()) / 1000;
        statisticsAverage.add(diffSeconds);
        synchronized (throughputMap){
            Integer tmp = throughputMap.get(diffSeconds);
            if (tmp == null) {
                throughputMap.put(diffSeconds, 1);
            } else {
                throughputMap.put(diffSeconds, tmp + 1);
            }
        }
    }


    public void getWrite() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("10threads.txt"));
        for (Map.Entry<Long, Integer> entry : throughputMap.entrySet()) {
            writer.write(entry.getKey() + " " + entry.getValue());
            writer.newLine();
        }
        writer.close();
    }

    public void statistics() throws IOException {
        double sum1 = 0;
        for (Integer value : throughputMap.values()) {
            sum1 += value;
        }
        double avgThroughput = sum1 / throughputMap.size();
        System.out.println("Average throughput: " + avgThroughput + " messages/sec");
        double sum = 0.0;
        for (double difftime : statisticsAverage) sum += difftime;
        double avgLatency = sum / statisticsAverage.size();
        System.out.println("Average latency: " + avgLatency);
        BufferedWriter writer1 = new BufferedWriter(new FileWriter("latency.txt"));
        writer1.write(cnt + " " + avgLatency);
        writer1.close();
    }

}
