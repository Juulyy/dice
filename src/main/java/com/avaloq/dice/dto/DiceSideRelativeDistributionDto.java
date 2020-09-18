package com.avaloq.dice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties
public class DiceSideRelativeDistributionDto {
    private int totalRollsSum;
    private long totalQuantity;
    private double relativeDistribution;
}