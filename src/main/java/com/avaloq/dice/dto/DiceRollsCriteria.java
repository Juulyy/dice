package com.avaloq.dice.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class DiceRollsCriteria {
    @Min(1)
    private int diceNumber = 3;
    @Min(4)
    private int diceSide = 6;
    @Min(1)
    private long totalRollsNumber = 100;

}
