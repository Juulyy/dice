package com.avaloq.dice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DiceSimulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int diceNumber;
    private int diceSide;
    private long totalRollsNumber;

    @OneToMany(mappedBy = "diceSimulation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SimulationResult> simulationsResultList;
}
