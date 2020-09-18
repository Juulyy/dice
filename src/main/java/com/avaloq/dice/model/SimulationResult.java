package com.avaloq.dice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "simulation_result")
public class SimulationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int totalRollsSum;
    private long quantity;

    @ManyToOne
    @JoinColumn(name = "dice_simulation_fk", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "dice_simulation_fk", value = ConstraintMode.CONSTRAINT))
    private DiceSimulation diceSimulation;

}
