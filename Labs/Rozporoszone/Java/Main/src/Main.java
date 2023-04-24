import pl.janek.MyListSynchronized;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {
    static public List list1 = new LinkedList();
    static public List list2 = new LinkedList();

    static public List result = new MyListSynchronized();

    public static void main(String[] args) {
        Random rand = new Random();
        for (int i = 0; i< 100; i++){
            list1.add((int) ((Math.random() * 80 +1)));
        }
        for (int i = 0; i< 100; i++){
            list2.add((int) ((Math.random() * 80 + 1)));
        }
        System.out.println(list1);
        System.out.println(list2);

        Runnable myThreadLst1 = () ->
        {
            for (int i = 0; i< list1.size();i++){
                result.add(list1.get(i));
            };
        };

        Runnable myThreadLst2 = () ->
        {
            for (int i = 0; i< list2.size();i++){
                result.add(list2.get(i));
            };
        };

        Thread run = new Thread(myThreadLst1);
        Thread run2 = new Thread(myThreadLst2);
        run.start();
        run2.start();


        try {
            run.join();
            run2.join();
        } catch (InterruptedException e){
            System.out.println("blad w catchu");
        }



        System.out.println("Poczatkowa lista1 rozmiar: " + list1.size());
        System.out.println("Poczatkowa lista2 rozmiar: " + list2.size());
        System.out.println(result.stream().sorted().toList());

        System.out.println("rozmiar: " + result.size());
    }

}
