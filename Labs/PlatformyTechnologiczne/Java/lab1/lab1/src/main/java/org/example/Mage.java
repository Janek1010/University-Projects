package org.example;

import com.sun.jdi.Value;

import java.util.*;

import static java.util.Map.Entry.comparingByValue;


public class Mage implements Comparable<Mage> {
    private static String currentMode = "";
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices;

    private static  Map<Mage,Integer> statistics;
    public Mage(String name, int level, double power) {
        this.name = name;
        this.level = level;
        this.power = power;
        switch (this.currentMode){
            case "natural" -> {
                this.apprentices = new TreeSet<>();
                this.statistics = new TreeMap<>();
            }
            case "bylevel" -> {
                this.apprentices = new TreeSet<>(new ComparingByLevel());
                this.statistics = new TreeMap<>(new ComparingByLevel());
                // bez sortowania, metoda compareTo dalej jest ale jak to jest hashSet to i tak nie jest sortowane
            }
            default -> {
                this.apprentices = new HashSet<>();
                statistics = new HashMap<>();
            }
        }
    }

    public String getName() {
        return name;
    }
    public static Map<Mage,Integer> getStatistics(Mage rootMage){
        statistics.clear();
        makeMap(rootMage);
        return statistics;
    }
    private static int makeMap(Mage rootMage){
        var apprentices =  rootMage.getApprentices();
        int size = apprentices.size();
        if (size == 0){
            return 0;
        }
        int count = statistics.getOrDefault(rootMage, 0);
        statistics.put(rootMage, count + size); // dodaje najpeirw bezposrednie dzieci, potem tyle iel dzieci maja dzieci

        for (var appr: apprentices) {
            int childrenValue = makeMap(appr);
            count = statistics.getOrDefault(rootMage, 0);
            statistics.put(rootMage, count + childrenValue); // dodaje najpeirw bezposrednie dzieci, potem tyle iel dzieci maja dzieci
        }
        return statistics.get(rootMage);
    }

    public static String getCurrentMode() {
        return currentMode;
    }

    public static void setCurrentMode(String currentMode) {
        Mage.currentMode = currentMode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public Set<Mage> getApprentices() {
        return apprentices;
    }
    public boolean addMage(Mage mage){
        if (this.apprentices.add(mage)){
            return true;
        }
        System.out.println("Nie udalo sie dodac, obiekt juz istnieje w zbiorze!");
        return false;
    }
    public void setApprentices(Set<Mage> apprentices) {
        this.apprentices = apprentices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mage mage)) return false;

        if (level != mage.level) return false;
        if (Double.compare(mage.power, power) != 0) return false;
        return Objects.equals(name, mage.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + level;
        temp = Double.doubleToLongBits(power);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public int compareTo(Mage o) {
        return Comparator.comparing(Mage::getName)
                .reversed()
                .thenComparing(Mage::getLevel)
                .thenComparing(Mage::getPower)
                .reversed()
                .compare(this, o);
    }

    @Override
    public String toString() {
        return "Mage{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", power=" + power +
                '}';
    }
}
