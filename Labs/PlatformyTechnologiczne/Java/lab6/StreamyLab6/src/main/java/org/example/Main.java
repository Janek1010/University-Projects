package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {


        List<Path> files;
        Path source = Path.of("C:\\Users\\janek\\MyCodes\\University-Projects\\Labs\\PlatformyTechnologiczne\\Java\\lab6\\StreamyLab6\\src\\main\\resources\\pictures");
        try (Stream<Path> stream = Files.list(source)) {
            files = stream.collect(Collectors.toList());
        } catch (IOException e) {
        }


    }
}