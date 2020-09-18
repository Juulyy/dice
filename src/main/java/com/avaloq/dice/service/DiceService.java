package com.avaloq.dice.service;

import com.avaloq.dice.dto.DiceRollsCriteria;
import com.avaloq.dice.dto.DiceSimulationDto;
import com.avaloq.dice.dto.GroupedDiceSideCombinationWithRelativeDistributionDto;
import com.avaloq.dice.model.DiceSimulation;
import com.avaloq.dice.model.SimulationResult;
import com.avaloq.dice.repository.DiceSimulationRepository;
import com.avaloq.dice.repository.SimulationResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
public class DiceService {

    private final DiceSimulationRepository diceSimulationRepository;

    private final SimulationResultRepository simulationResultRepository;

    private final SplittableRandom randomGenerator = new SplittableRandom();

    public DiceService(DiceSimulationRepository diceSimulationRepository, SimulationResultRepository simulationResultRepository) {
        this.diceSimulationRepository = diceSimulationRepository;
        this.simulationResultRepository = simulationResultRepository;
    }

    public Map<Integer, Long> rollDices(DiceRollsCriteria diceRollsCriteria) {
        return LongStream.rangeClosed(1, diceRollsCriteria.getTotalRollsNumber())
                .mapToObj(roll -> getRollResult(diceRollsCriteria))
                .collect(groupingBy(el -> el, counting()));
    }

    public void saveDiceSimulation(DiceSimulation diceSimulation) {
        diceSimulationRepository.save(diceSimulation);
    }

    public List<GroupedDiceSideCombinationWithRelativeDistributionDto> getNumberDiceSideCombinationWithRelativeDistribution() {
        return diceSimulationRepository.getNumberDiceSideCombinationWithRelativeDistribution();
    }

    public void saveDiceResultSimulation(DiceSimulation diceSimulation, List<SimulationResult> simulationResultList) {
        simulationResultList.forEach(el -> el.setDiceSimulation(diceSimulation));
        diceSimulation.setSimulationsResultList(simulationResultList);
        saveDiceSimulation(diceSimulation);
    }

    private int getRollResult(DiceRollsCriteria diceRollsCriteria) {
        return IntStream.rangeClosed(1, diceRollsCriteria.getDiceNumber())
                .map(dice -> randomGenerator.nextInt(1, diceRollsCriteria.getDiceSide()))
                .sum();
    }
}

