package com.avaloq.dice.repository;

import com.avaloq.dice.dto.DiceSimulationDto;
import com.avaloq.dice.dto.GroupedDiceSideCombinationWithRelativeDistributionDto;
import com.avaloq.dice.model.DiceSimulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiceSimulationRepository extends JpaRepository<DiceSimulation, Long> {

    @Query("select new com.avaloq.dice.dto.DiceSimulationDto(entity.diceNumber, entity.diceSide, sum(entity.totalRollsNumber)) from DiceSimulation entity group by entity.diceNumber, entity.diceSide")
    List<DiceSimulationDto> getGroupedDiceSimulation();

    @Query(value = "select \n" +
            "   grouped_ds_by_id.DICE_NUMBER as diceNumber,\n" +
            "   grouped_ds_by_id.DICE_SIDE as diceSide, \n" +
            "   grouped_ds_by_id.TOTAL_ROLLS_AMOUNT as totalRollsAmount,\n" +
            "   sr.TOTAL_ROLLS_SUM as totalRollsSum, \n" +
            "   sum(sr.QUANTITY) as totalQuantity,\n" +
            "   sum(sr.QUANTITY) / grouped_ds_by_id.TOTAL_ROLLS_AMOUNT * 100 as relativeDistribution\n" +
            "from\n" +
            "(select \n" +
            "   ds.id, \n" +
            "   grouped_ds.* \n" +
            " from \n" +
            "   DICE_SIMULATION as ds,\n" +
            "   (select DICE_NUMBER, DICE_SIDE, sum(TOTAL_ROLLS_NUMBER) as TOTAL_ROLLS_AMOUNT from DICE_SIMULATION group by DICE_NUMBER, DICE_SIDE) as grouped_ds\n" +
            " where ds.DICE_NUMBER = grouped_ds.DICE_NUMBER and ds.DICE_SIDE = grouped_ds.DICE_SIDE) as grouped_ds_by_id, \n" +
            " SIMULATION_RESULT as sr\n" +
            "where grouped_ds_by_id.id = sr.DICE_SIMULATION_FK\n" +
            "group by grouped_ds_by_id.DICE_NUMBER,\n" +
            "   grouped_ds_by_id.DICE_SIDE, \n" +
            "   grouped_ds_by_id.TOTAL_ROLLS_AMOUNT,\n" +
            "   sr.TOTAL_ROLLS_SUM;", nativeQuery = true)
    List<GroupedDiceSideCombinationWithRelativeDistributionDto> getNumberDiceSideCombinationWithRelativeDistribution();
}
