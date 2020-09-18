package com.avaloq.dice.dto;

public interface GroupedDiceSideCombinationWithRelativeDistributionDto {
    int getDiceNumber();

    int getDiceSide();

    long getTotalRollsAmount();

    int getTotalRollsSum();

    long getTotalQuantity();

    double getRelativeDistribution();
}
