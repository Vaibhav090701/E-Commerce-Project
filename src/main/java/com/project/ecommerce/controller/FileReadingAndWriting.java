package com.project.ecommerce.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FileReadingAndWriting {
	
    private static final String INPUT_FILE = "src/input.txt";
    private static final String OUTPUT_FILE = "src/output.txt";
    private static final int QUEUE_CAPACITY = 100;
    private static final String END_MARKER = "EOF"; // Special marker to stop the writer

    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue(QUEUE_CAPACITY);

        Thread readerThread = new Thread(new ReaderTask(queue, INPUT_FILE));
        Thread writerThread = new Thread(new WriterTask(queue, OUTPUT_FILE));

        readerThread.start();
        writerThread.start();
    }
}

// Reader Task: Reads lines from a file and puts them into the queue
class ReaderTask implements Runnable {
    private BlockingQueue<String> queue;
    private String inputFile;

    public ReaderTask(BlockingQueue<String> queue, String inputFile) {
        this.queue = queue;
        this.inputFile = inputFile;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                queue.put(line); // blocks if the queue is full
            }
            queue.put("EOF"); // signal end of file
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Writer Task: Takes lines from the queue and writes them to a file
class WriterTask implements Runnable {
    private BlockingQueue<String> queue;
    private String outputFile;

    public WriterTask(BlockingQueue<String> queue, String outputFile) {
        this.queue = queue;
        this.outputFile = outputFile;
    }

    @Override
    public void run() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            while (true) {
                String line = queue.take(); // blocks if queue is empty
                if ("EOF".equals(line)) break;
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
