import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        List<Integer> list = new ArrayList<>();
        List<Integer> checked = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }

        File file = new File("shared_memory.txt");
        if (args.length == 0) {
            String path = System.getProperty("java.class.path");
            String pid = String.valueOf(ProcessHandle.current().pid());
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/k", "start", "java", "-cp", path, "Main", "processInitiate", pid);
            processBuilder.inheritIO();
            Process process1 = processBuilder.start();
            boolean poczekaj = false;
            for (int j = 0; j < list.size(); j++) {
                if (poczekaj){
                    poczekaj = false;
                    Thread.sleep(100);
                }
                if ((list.get(j) % 2) == 0) {
                    if ((list.get(j) % 3) == 0) {
                        int numberToCheck = list.get(j);
                        RandomAccessFile rafCheck = new RandomAccessFile(file, "rw");
                        FileChannel fileChannelCheck = rafCheck.getChannel();
                        FileLock fileLockCheck = fileChannelCheck.lock();
                        try {
                            int fileLength = (int) file.length();
                            if (fileLength >0) {

                                ByteBuffer buffer = ByteBuffer.allocate(fileLength);
                                int bytesRead = fileChannelCheck.read(buffer);
                                buffer.flip();
                                String data = new String(buffer.array(), 0, bytesRead);
                                String[] parts = data.split(System.lineSeparator());
                                int[] values = new int[parts.length];
                                for (int i = 0; i < parts.length; i++) {
                                    values[i] = Integer.parseInt(parts[i]);
                                }

                                List<Integer> valuess = Arrays.stream(values).boxed().toList();
                                boolean czyZawiera = valuess.contains(numberToCheck);
                                if (czyZawiera) {
                                    System.out.println("drugi proces juz wyswietlil wiec nie wyswietlam liczby o numerze:" + numberToCheck);
                                } else {
                                    System.out.println("wyswietlam po raz pierwszy liczbę:" + numberToCheck);
                                    poczekaj = true;
                                }
                            }

                            // jakos odczytac te dane
                        } finally {
                            fileLockCheck.release(); // odblokowanie pliku
                            fileChannelCheck.close(); // zamknięcie kanału
                            rafCheck.close(); // zamknięcie pliku
                        }
                    }
                    checked.add(j);

                    RandomAccessFile raf = new RandomAccessFile(file, "rw");
                    FileChannel fileChannel = raf.getChannel();
                    FileLock fileLock = fileChannel.lock();
                    try {
                        String s = Integer.toString(j) + System.lineSeparator();
                        ByteBuffer buf = ByteBuffer.wrap(s.getBytes());
                        fileChannel.position(file.length()); // ustawienie pozycji na koniec pliku
                        fileChannel.write(buf); // dopisanie liczby do pliku
                    } finally {
                        fileLock.release(); // odblokowanie pliku
                        fileChannel.close(); // zamknięcie kanału
                        raf.close(); // zamknięcie pliku
                    }

                }

            }
            System.exit(0);
            return;
        }
        boolean poczekaj = false;
        for (int j = 0; j < list.size(); j++) {
            if (poczekaj){
                poczekaj = false;
                Thread.sleep(100);
            }
            //Thread.sleep(1);
            if ((list.get(j) % 3) == 0) {
                if ((list.get(j) % 2) == 0) {
                    int numberToCheck = list.get(j);
                    RandomAccessFile rafCheck = new RandomAccessFile(file, "rw");
                    FileChannel fileChannelCheck = rafCheck.getChannel();
                    FileLock fileLockCheck = fileChannelCheck.lock();
                    try {
                        int fileLength = (int) file.length();
                        if (fileLength >0) {
                            ByteBuffer buffer = ByteBuffer.allocate(fileLength);
                            int bytesRead = fileChannelCheck.read(buffer);
                            buffer.flip();
                            String data = new String(buffer.array(), 0, bytesRead);
                            String[] parts = data.split(System.lineSeparator());
                            int[] values = new int[parts.length];
                            for (int i = 0; i < parts.length; i++) {
                                values[i] = Integer.parseInt(parts[i]);
                            }

                            List<Integer> valuess = Arrays.stream(values).boxed().toList();
                            boolean czyZawiera = valuess.contains(numberToCheck);
                            if (czyZawiera) {
                                System.out.println("drugi proces juz wyswietlil wwiec nie wyswietlam liczby o numerze:" + numberToCheck);
                            } else {
                                System.out.println("wyswietlam po raz pierwszy liczbę:" + numberToCheck);
                                poczekaj = true;
                            }
                        }

                        // jakos odczytac te dane
                    } finally {
                        fileLockCheck.release(); // odblokowanie pliku
                        fileChannelCheck.close(); // zamknięcie kanału
                        rafCheck.close(); // zamknięcie pliku
                    }
                }
                checked.add(j);

                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                FileChannel fileChannel = raf.getChannel();
                FileLock fileLock = fileChannel.lock();
                try {
                    String s = Integer.toString(j) + System.lineSeparator();
                    ByteBuffer buf = ByteBuffer.wrap(s.getBytes());
                    fileChannel.position(file.length()); // ustawienie pozycji na koniec pliku
                    fileChannel.write(buf); // dopisanie liczby do pliku
                } finally {
                    fileLock.release(); // odblokowanie pliku
                    fileChannel.close(); // zamknięcie kanału
                    raf.close(); // zamknięcie pliku
                }

            }
        }
        System.exit(0);
        return;
    }

}
