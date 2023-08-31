package org.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mage {

    @Id
    private String name;
    private int level;

    @ManyToOne
    private Tower tower;


    // konstruktory, gettery i settery
}
