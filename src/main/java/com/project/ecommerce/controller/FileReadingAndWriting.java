package com.project.ecommerce.controller;

import java.io.*;
import java.util.*;

public class FileReadingAndWriting {
    static int turn = 0; // 0 = thread 1, 1 = thread 2, etc.
    static final Object lock = new Object();

	public static void main(String[] args) throws FileNotFoundException, IOException {
        // Read numbers from file
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        // Define thread ranges manually
        List<List<String>> threadData = new ArrayList<>();
        threadData.add(lines.subList(0, 4));   // Thread 0: lines 0-3
        threadData.add(lines.subList(4, 8));   // Thread 1: lines 4-7
        threadData.add(lines.subList(8, 12));  // Thread 2: lines 8-11
        threadData.add(lines.subList(12, 15)); // Thread 3: lines 12-14

        int threadCount = threadData.size();

        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            final List<String> data = threadData.get(i);

            new Thread(() -> {
                for (int i1 = 0; i1 < data.size(); i1++) {
                    synchronized (lock) {
                        while (turn != threadId) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        // Print this cycle's item if available
                        if (i1 < data.size()) {
                            System.out.println("Thread " + (threadId + 1) + ": " + data.get(i1));
                        }

                        // Move to next thread
                        turn = (turn + 1) % threadCount;
                        lock.notifyAll();
                    }
                }
            }).start();
        }
    }

	}

