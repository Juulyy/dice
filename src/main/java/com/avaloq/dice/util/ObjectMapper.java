package com.avaloq.dice.util;

import com.avaloq.dice.dto.*;
import com.avaloq.dice.model.DiceSimulation;
import com.avaloq.dice.model.SimulationResult;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

@Component
public class ObjectMapper {
    public List<ResultedDiceSideCombinationWithRelativeDistributionDto> getResultedDiceSideCombinationWithRelativeDistribution(List<GroupedDiceSideCombinationWithRelativeDistributionDto> groupedSimulationsAndTotalRolls) {
        List<ResultedDiceSideCombinationWithRelativeDistributionDto> result = groupedSimulationsAndTotalRolls.stream()
                .map(el -> Pair.of(createDiceSimulationDto(el), createDiceSideRelativeDistributionDto(el)))
                .collect(groupingBy(Pair::getFirst, mapping(Pair::getSecond, toList())))
                .entrySet()
                .stream()
                .map(entry -> new ResultedDiceSideCombinationWithRelativeDistributionDto(entry.getKey(), entry.getValue()))
                .collect(toList());
        return result;
    }

    private DiceSideRelativeDistributionDto createDiceSideRelativeDistributionDto(GroupedDiceSideCombinationWithRelativeDistributionDto el) {
        return DiceSideRelativeDistributionDto.builder()
                .totalQuantity(el.getTotalQuantity())
                .totalRollsSum(el.getTotalRollsSum())
                .relativeDistribution(el.getRelativeDistribution())
                .build();
    }

    private DiceSimulationDto createDiceSimulationDto(GroupedDiceSideCombinationWithRelativeDistributionDto el) {
        return DiceSimulationDto.builder()
                .diceNumber(el.getDiceNumber())
                .diceSide(el.getDiceSide())
                .totalRollsAmount(el.getTotalRollsAmount())
                .build();
    }

    public List<DiceSummaryRollsDto> convertToEntity(Map<Integer, Long> resultedList) {
        return resultedList.entrySet().stream()
                .map(item -> DiceSummaryRollsDto.builder()
                        .totalRollsSum(item.getKey())
                        .quantity(item.getValue())
                        .build()).collect(Collectors.toList());
    }

    public DiceSimulation convertToEntity(DiceRollsCriteria diceRollsCriteria) {
        return DiceSimulation.builder()
                .diceNumber(diceRollsCriteria.getDiceNumber())
                .diceSide(diceRollsCriteria.getDiceSide())
                .totalRollsNumber(diceRollsCriteria.getTotalRollsNumber())
                .build();
    }

    public List<SimulationResult> convertToEntity(List<DiceSummaryRollsDto> rollsListDto) {
        return rollsListDto.stream()
                .map(el -> SimulationResult.builder().totalRollsSum(el.getTotalRollsSum())
                        .quantity(el.getQuantity()).build())
                .collect(Collectors.toList());
    }
}
