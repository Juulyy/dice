package com.avaloq.dice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultedDiceSideCombinationWithRelativeDistributionDto {
    private DiceSimulationDto diceSimulation;
    private List<DiceSideRelativeDistributionDto> diceSideRelativeDistribution;
}
