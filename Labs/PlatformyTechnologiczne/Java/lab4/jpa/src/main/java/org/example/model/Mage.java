package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Mage {
    @Id
    private String name;
    private int level;
    @ManyToOne
    private Tower tower;

}
