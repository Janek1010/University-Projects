package org.example;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Main {
    private static final String[] names = {"Bartek","Maciek", "Michał", "Konrad", "Aleksander", "Oskar", "Kacper" };
    private final static int MAGES_AMOUNT = 50;
    /*
     setting up boundaries of apprentices,
     e.g. MIN - 2, MAX - 6 = first mage will have 3,4 or 5 apprentices
    */
    private final static int HOW_MANY_APPRENTICES_MIN = 1;
    private final static int HOW_MANY_APPRENTICES_MAX = 4;

    public static void main(String[] args) {
        if (args.length >0){
            Mage.setCurrentMode(args[0]);
        }

        var rootMage = populateData();

        System.out.println(rootMage);
        printOut(rootMage,1);
        System.out.println();

        Mage.getStatistics(rootMage)
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(System.out::println);

//        for (var entry : Mage.getStatistics(rootMage).entrySet()) {
//           System.out.println(entry.getKey() + " - "+ entry.getValue());
//        }

    }

    private static Mage populateData(){
        Mage mageRoot = new Mage("Merlin", 700 ,1400);
        addApprentices( mageRoot,MAGES_AMOUNT-1,0);
        return mageRoot;
    }
    private static void addApprentices(Mage mage,int remainingMages, int depth){
        var rand = new Random();
        int howManyApprentices = rand.nextInt(HOW_MANY_APPRENTICES_MIN,HOW_MANY_APPRENTICES_MAX)+depth; // ile potomkow losujemy
        for (int i = 0; i < howManyApprentices; i++) {
            if (remainingMages <= 0 ){
                return;
            }
            var newMage = new Mage(names[rand.nextInt(0, names.length)], rand.nextInt(400 - 70 * depth,500 - 70 * depth) ,rand.nextInt(1000- 130 * depth,1200 - 150 * depth));
            remainingMages--;
            mage.addMage(newMage);
            addApprentices(newMage, (remainingMages/howManyApprentices), depth+1);

        }
    }
    private static void printOut(Mage mage, int depth){
        var apprentices = mage.getApprentices();

        if (apprentices.size() > 0){
            for ( var apprentice : apprentices) {
                System.out.println(StringUtils.repeat(' ',depth*4)+ apprentice);
                printOut(apprentice, depth+1);
            }
        }
    }
}