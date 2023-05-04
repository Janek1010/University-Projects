package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Tower {
    @Id
    private String name;
    private int height;
    @OneToMany
    private List<Mage> mages;
}
