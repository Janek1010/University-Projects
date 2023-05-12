package org.example;

import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImageProcessor {
    public static void processImagesSequential(String startingDirectory, String destinationDirector) throws IOException {
        Path source = Path.of(startingDirectory);
        try (Stream<Path> stream = Files.list(source)) {
            List<Path> files = stream.collect(Collectors.toList());
            files.stream().map(path -> {
                        try {
                            BufferedImage image = ImageIO.read(path.toFile());
                            return Pair.of(path.getFileName().toString(), image);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(pair -> pair != null)
                    .map(pair -> {
                        BufferedImage original = pair.getValue();
                        BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
                        for (int i = 0; i < original.getWidth(); i++) {
                            for (int j = 0; j < original.getHeight(); j++) {
                                int rgb = original.getRGB(i, j);
                                Color color = new Color(rgb);
                                int red = color.getRed();
                                int blue = color.getBlue();
                                int green = color.getGreen();
                                Color outColor = new Color(red, blue, green);
                                int outRgb = outColor.getRGB();
                                image.setRGB(i, j, outRgb);
                            }
                        }
                        return Pair.of(pair.getKey(), image);
                    }).forEach(pair -> {
                        try {
                            String fileName = pair.getKey();
                            BufferedImage image = pair.getValue();
                            File output = new File(destinationDirector + File.separator + fileName);
                            ImageIO.write(image, "jpg", output);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

        } catch (IOException e) {
            System.out.println("blad przy wczytywaniu!");
        }

    }
    public static void processImagesParallel(String startingDirectory, String destinationDirector) throws IOException {
        Path source = Path.of(startingDirectory);
        try (Stream<Path> stream = Files.list(source)) {
            List<Path> files = stream.collect(Collectors.toList());
            files.parallelStream().map(path -> {
                        try {
                            BufferedImage image = ImageIO.read(path.toFile());
                            return Pair.of(path.getFileName().toString(), image);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(pair -> pair != null)
                    .map(pair -> {
                        BufferedImage original = pair.getValue();
                        BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
                        for (int i = 0; i < original.getWidth(); i++) {
                            for (int j = 0; j < original.getHeight(); j++) {
                                int rgb = original.getRGB(i, j);
                                Color color = new Color(rgb);
                                int red = color.getRed();
                                int blue = color.getBlue();
                                int green = color.getGreen();
                                Color outColor = new Color(red, blue, green);
                                int outRgb = outColor.getRGB();
                                image.setRGB(i, j, outRgb);
                            }
                        }
                        return Pair.of(pair.getKey(), image);
                    }).forEach(pair -> {
                        try {
                            String fileName = pair.getKey();
                            BufferedImage image = pair.getValue();
                            File output = new File(destinationDirector + File.separator + fileName);
                            ImageIO.write(image, "jpg", output);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

        } catch (IOException e) {
            System.out.println("blad przy wczytywaniu!");
        }

    }
}
