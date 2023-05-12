package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String destinationDirectory = args[0];
        String startingDirectory = args[1];

        long time = System.currentTimeMillis();
        ImageProcessor.processImagesParallel(startingDirectory, destinationDirectory);
        System.out.println("Wykonane za pomoca operacji rownoleglych "+(System.currentTimeMillis() - time)/1000.0+" sekund");

        long time2 = System.currentTimeMillis();
        ImageProcessor.processImagesParallel(startingDirectory, destinationDirectory);
        System.out.println("Wykonane za pomoca operacji sekwencyjnych "+(System.currentTimeMillis() - time)/1000.0+" sekund");
    }
}