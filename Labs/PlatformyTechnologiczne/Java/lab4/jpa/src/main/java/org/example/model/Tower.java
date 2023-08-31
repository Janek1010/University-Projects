package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
public class Tower {

    @Id
    private String name;
    private int height;

    @OneToMany(mappedBy = "tower")
    private List<Mage> mages = new ArrayList<>();

    public Tower(String name, int height) {
        this.name = name;
        this.height = height;
        this.mages = new ArrayList<>();
    }

    public Tower() {
        this.mages = new ArrayList<>();
    }

    public Tower(String name, int height, List<Mage> mages) {
        this.name = name;
        this.height = height;
        this.mages = mages;
    }

    public List<Mage> getMages() {
        if (this.mages == null){
            this.mages = new ArrayList<>();
        }
        return mages;
    }
}
