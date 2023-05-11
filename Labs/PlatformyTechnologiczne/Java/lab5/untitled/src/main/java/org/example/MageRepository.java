package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MageRepository {
    private Map<String, Mage> collection = new HashMap<>();

    public Optional<Mage> find(String name) {
        Mage mage = collection.get(name);
        return Optional.ofNullable(mage);
    }
    public void delete(String name) {
//        próba usunięcia nieistniejącego obiektu powoduje zwrócenie obiektu String o wartości done,
//        próba usunięcia nieistniejącego obiektu powoduje zwrócenie obiektu String o wartości not found,

        if(collection.containsKey(name)){
            collection.remove(name);
            return;
        }
        throw new IllegalArgumentException();
    }
    public void save(Mage mage) {
        if (collection.containsKey(mage.getName())){
            throw new IllegalArgumentException();
        }
        else {
            collection.put(mage.getName(), mage);
        }
    }

}