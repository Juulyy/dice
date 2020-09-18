package com.avaloq.dice.repository;

import com.avaloq.dice.model.SimulationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationResultRepository extends JpaRepository<SimulationResult, Long> {
}
