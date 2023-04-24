package org.example;

import java.util.Comparator;

public class ComparingByLevel implements Comparator<Mage> {
    @Override
    public int compare(Mage o1, Mage o2) {
        return Comparator.comparing(Mage::getLevel)
                .reversed()
                .thenComparing(Mage::getName)
                .reversed()
                .thenComparing(Mage::getPower)
                .reversed()
                .compare(o1, o2);
    }
}
