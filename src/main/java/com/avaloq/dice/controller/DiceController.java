package com.avaloq.dice.controller;

import com.avaloq.dice.dto.DiceRollsCriteria;
import com.avaloq.dice.dto.DiceSummaryRollsDto;
import com.avaloq.dice.dto.GroupedDiceSideCombinationWithRelativeDistributionDto;
import com.avaloq.dice.dto.ResultedDiceSideCombinationWithRelativeDistributionDto;
import com.avaloq.dice.model.DiceSimulation;
import com.avaloq.dice.model.SimulationResult;
import com.avaloq.dice.service.DiceService;
import com.avaloq.dice.util.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@Validated
public class DiceController {

    private final DiceService diceService;

    private final ObjectMapper objectMapper;

    public DiceController(DiceService diceService, ObjectMapper objectMapper) {
        this.diceService = diceService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/gameResult")
    public ResponseEntity<List<DiceSummaryRollsDto>> rollDice(@Valid DiceRollsCriteria diceRollsCriteria, Errors errors) {

        List<DiceSummaryRollsDto> rollsDto = objectMapper.convertToEntity(diceService.rollDices(diceRollsCriteria));
        DiceSimulation diceSimulation = objectMapper.convertToEntity(diceRollsCriteria);
        List<SimulationResult> simulationResultList = objectMapper.convertToEntity(rollsDto);

        diceService.saveDiceResultSimulation(diceSimulation, simulationResultList);

        return ResponseEntity.ok(rollsDto);
    }

    @GetMapping("/simulationsNumber")
    public ResponseEntity<List<ResultedDiceSideCombinationWithRelativeDistributionDto>> getSimulationsAndTotalRollsMade() {
        List<GroupedDiceSideCombinationWithRelativeDistributionDto> groupedSimulationsAndTotalRolls =
                diceService.getNumberDiceSideCombinationWithRelativeDistribution();

        List<ResultedDiceSideCombinationWithRelativeDistributionDto> result =
                objectMapper.getResultedDiceSideCombinationWithRelativeDistribution(groupedSimulationsAndTotalRolls);

        return ResponseEntity.ok(result);
    }
}
