package org.example;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MageController {
    private MageRepository repository;
    public MageController(MageRepository repository) {
        this.repository = repository;
    }
    public String find(String name) {
        Optional<Mage> mage = repository.find(name);
        if (mage.isPresent()){
            return mage.get().toString();
        }
        return "Not Found";
    }
    public String delete(String name) {
        try{
            repository.delete(name);
            return "Done";
        } catch (IllegalArgumentException e){
            return "Not Found";
        }
    }
    public String save(String name, String level) {
        try {
            repository.save(new Mage(name,Integer.parseInt(level)));
            return "Done";
        }
        catch (IllegalArgumentException il){
            return "Bad Request";
        }
    }
}
